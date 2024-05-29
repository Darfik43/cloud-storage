package com.darfik.cloudstorage.service.impl;

import com.darfik.cloudstorage.dto.FolderRenameRequest;
import com.darfik.cloudstorage.dto.FolderUploadRequest;
import com.darfik.cloudstorage.exception.FileOperationException;
import com.darfik.cloudstorage.service.AppFolderService;
import com.darfik.cloudstorage.service.UserService;
import com.darfik.cloudstorage.service.props.MinioProperties;
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
public class AppFolderServiceImpl implements AppFolderService {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;
    private final UserService userService;

    private String getUserBucketPrefix(String email) {
        return "user-" + userService.getUserIdByEmail(email) + "-files/";
    }

    @Override
    public void uploadFolder(FolderUploadRequest folderUploadRequest) {
        List<MultipartFile> files = folderUploadRequest.getFiles();
        List<SnowballObject> objects = createSnowballObjects(files,
                getUserBucketPrefix(folderUploadRequest.getOwner()));
        uploadObjects(objects);
    }

    @Override
    public void renameFolder(FolderRenameRequest folderRenameRequest) {
        String oldPrefix =
                getUserBucketPrefix(folderRenameRequest.getOwner()) + folderRenameRequest.getCurrentName() + "/";
        String newPrefix =
                getUserBucketPrefix(folderRenameRequest.getOwner()) + folderRenameRequest.getNewName() + "/";
        renameFolderObjects(oldPrefix, newPrefix);
    }

    private List<SnowballObject> createSnowballObjects(List<MultipartFile> files, String userPrefix) {
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = userPrefix + file.getOriginalFilename();
            try {
                objects.add(new SnowballObject(fileName,
                        file.getInputStream(), file.getSize(), null));
            } catch (Exception e) {
                throw new FileOperationException("Error uploading folder: " + e.getMessage());
            }
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
            throw new FileOperationException("Error uploading folder: " + e.getMessage());
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

            List<SnowballObject> newObjects = new ArrayList<>();
            for (Result<Item> result : objects) {
                Item item = result.get();
                String newObjectName =
                        newPrefix + item.objectName().substring(oldPrefix.length());
                newObjects.add(createSnowballObjectFromItem(item,
                        newObjectName));
            }

            deleteOldObjects(objects);
            uploadObjects(newObjects);

        } catch (Exception e) {
            throw new FileOperationException("Error renaming folder: " + e.getMessage());
        }
    }

    private SnowballObject createSnowballObjectFromItem(Item item,
                                                        String newObjectName) {
        try {
            return new SnowballObject(newObjectName,
                    minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(item.objectName())
                    .build()), item.size(), ZonedDateTime.now());
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
}

