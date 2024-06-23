package com.darfik.cloudstorage.domain.s3storage.folder.impl;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.s3storage.file.dto.FileResponse;
import com.darfik.cloudstorage.domain.s3storage.file.impl.S3FileMinioService;
import com.darfik.cloudstorage.domain.s3storage.folder.S3FolderService;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderUploadRequest;
import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
import com.darfik.cloudstorage.domain.s3storage.util.UserFolderResolver;
import com.darfik.cloudstorage.domain.user.UserService;
import io.minio.*;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import io.minio.messages.DeleteError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FolderMinioService implements S3FolderService {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;
    private final UserService userService;
    private final S3FileMinioService s3FileMinioService;

    @Override
    public void uploadFolder(FolderUploadRequest folderUploadRequest, String owner) {
        List<MultipartFile> files = folderUploadRequest.files();
        List<SnowballObject> objects = createSnowballObjectsFromFiles(files, getUserFolderPrefix(owner));

        uploadObjects(objects);
    }

    @Override
    public void renameFolder(FolderRenameRequest folderRenameRequest, String owner) {
        String oldPrefix = getUserFolderPrefix(owner) + folderRenameRequest.path() + folderRenameRequest.currentName();
        String newPrefix = getUserFolderPrefix(owner) + folderRenameRequest.path() +  folderRenameRequest.newName() + "/";

        renameFolderObjects(oldPrefix, newPrefix);
    }

    @Override
    public void deleteFolder(FolderDeleteRequest folderDeleteRequest, String owner) {
        List<FileResponse> files = s3FileMinioService.getUserFiles(owner, folderDeleteRequest.path() + folderDeleteRequest.name());

        List<DeleteObject> objects = convertToDeleteObjects(files, owner);

        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .objects(objects)
                .build());

        results.forEach(deleteErrorResult -> {
            try {
                deleteErrorResult.get();
            }
            catch (Exception e) {
                throw new FileOperationException("There is an error while deleting the folder, try again later");
            }
        });
    }

    private List<DeleteObject> convertToDeleteObjects(List<FileResponse> files, String owner) {
        List<DeleteObject> objects = new ArrayList<>();

        for (FileResponse file : files) {
            objects.add(new DeleteObject(getUserFolderPrefix(owner) + file.path() + file.name()));
        }

        return objects;
    }

    private void uploadObjects(List<SnowballObject> objects) {
        try {
            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .objects(objects)
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("Error uploading files: " + e.getMessage());
        }
    }

    private void renameFolderObjects(String oldPrefix, String newPrefix) {
        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .prefix(oldPrefix)
                            .recursive(true)
                            .build());

            List<SnowballObject> newObjects = createSnowballObjectsFromItems(objects, newPrefix, oldPrefix);

            uploadObjects(newObjects);
            deleteOldObjects(objects);
        } catch (Exception e) {
            throw new FileOperationException("Error renaming files: " + e.getMessage());
        }
    }

    private List<SnowballObject> createSnowballObjectsFromFiles(List<MultipartFile> files, String userPrefix) {
        List<SnowballObject> objects = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = userPrefix + file.getOriginalFilename();
            try {
                objects.add(new SnowballObject(fileName,
                        file.getInputStream(), file.getSize(), null));
            } catch (Exception e) {
                throw new FileOperationException("Error uploading files: " + e.getMessage());
            }
        }
        return objects;
    }

    private List<SnowballObject> createSnowballObjectsFromItems(Iterable<Result<Item>> items, String newPrefix, String oldPrefix) {
        try {
            List<SnowballObject> snowballObjects = new ArrayList<>();

            for (Result<Item> result : items) {
                Item item = result.get();
                snowballObjects.add(new SnowballObject(
                        newPrefix + item.objectName().substring(oldPrefix.length()),
                        minioClient.getObject(GetObjectArgs.builder()
                                .bucket(minioProperties.getBucket())
                                .object(item.objectName())
                                .build()),
                        item.size(),
                        ZonedDateTime.now()));
            }

            return snowballObjects;
        } catch (Exception e) {
            throw new FileOperationException("Error creating object: " + e.getMessage());
        }
    }

    private void deleteOldObjects(Iterable<Result<Item>> objects) throws Exception {
        for (Result<Item> result : objects) {
            Item item = result.get();
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(item.objectName())
                    .build());
        }
    }

    private String getUserFolderPrefix(String owner) {
        return UserFolderResolver.getUserFolderPrefix(userService.getUserIdByEmail(owner));
    }

}

