package com.darfik.cloudstorage.service.impl;

import com.darfik.cloudstorage.dto.FolderUploadRequest;
import com.darfik.cloudstorage.exception.FileOperationException;
import com.darfik.cloudstorage.service.AppFolderService;
import com.darfik.cloudstorage.service.UserService;
import com.darfik.cloudstorage.service.props.MinioProperties;
import io.minio.MinioClient;
import io.minio.SnowballObject;
import io.minio.UploadSnowballObjectsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppFolderServiceImpl implements AppFolderService {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;
    private final UserService userService;


    //It works
    @Override
    public void uploadFolder(FolderUploadRequest folderUploadRequest) {
        List<MultipartFile> files = folderUploadRequest.getFiles();
        List<SnowballObject> objects = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = generateUserPrefix(folderUploadRequest.getOwner()) + file.getOriginalFilename();
            try {
                SnowballObject snowballObject = new SnowballObject(
                        fileName,
                        file.getInputStream(),
                        file.getSize(),
                        null
                        );
                objects.add(snowballObject);
            } catch (Exception e) {
                throw new FileOperationException("Error occurred while uploading folder" + e.getMessage());
            }
        }
        try {
            minioClient.uploadSnowballObjects(UploadSnowballObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .objects(objects)
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("Error occurred while uploading folder" + e.getMessage());
        }
    }

    private String generateUserPrefix(String email) {
        return "user-" + userService.getByEmail(email).getId() + "-files/";
    }

}
