package com.darfik.cloudstorage.domain.s3storage.folder;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
import com.darfik.cloudstorage.domain.s3storage.util.UserFolderResolver;
import com.darfik.cloudstorage.domain.user.UserService;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinioS3FolderService implements S3FolderService {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;
    private final UserService userService;

    @Override
    public void uploadFolder(FolderUploadRequest folderUploadRequest, String owner) {
        List<MultipartFile> files = folderUploadRequest.files();
        List<SnowballObject> objects = createSnowballObjectsFromFiles(files, getUserFolderPrefix(owner));

        uploadObjects(objects);
    }

    @Override
    public void renameFolder(FolderRenameRequest folderRenameRequest, String owner) {
        String oldPrefix = getUserFolderPrefix(owner) + folderRenameRequest.currentName() + "/";
        String newPrefix = getUserFolderPrefix(owner) + folderRenameRequest.newName() + "/";

        renameFolderObjects(oldPrefix, newPrefix);
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

            List<SnowballObject> newObjects = createSnowballObjectsFromItems(objects, newPrefix);

            deleteOldObjects(objects);
            uploadObjects(newObjects);
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

    private List<SnowballObject> createSnowballObjectsFromItems(Iterable<Result<Item>> items, String newPrefix) {
        try {
            List<SnowballObject> snowballObjects = new ArrayList<>();

            for (Result<Item> result : items) {
                Item item = result.get();
                snowballObjects.add(new SnowballObject(
                        newPrefix,
                        minioClient.getObject(GetObjectArgs.builder()
                                .bucket(minioProperties.getBucket())
                                .object(item.objectName())
                                .build()), item.size(), ZonedDateTime.now()));
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

    private String getUserFolderPrefix(String email) {
        return UserFolderResolver.getUserFolderPrefix(userService.getUserIdByEmail(email));
    }

}

