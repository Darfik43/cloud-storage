package com.darfik.cloudstorage.service.impl;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.dto.FileDeleteRequest;
import com.darfik.cloudstorage.dto.FileRenameRequest;
import com.darfik.cloudstorage.dto.FileUploadRequest;
import com.darfik.cloudstorage.exception.FileOperationException;
import com.darfik.cloudstorage.service.AppFileService;
import com.darfik.cloudstorage.service.props.MinioProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppFileServiceImpl implements AppFileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final UserServiceImpl userService;

    @Override
    public void uploadFile(FileUploadRequest fileUploadRequest) {
        MultipartFile multipartFile = fileUploadRequest.getFile();

        String fileName =
                getUserFolderPrefix(fileUploadRequest.getOwner()) + fileUploadRequest.getFile().getOriginalFilename();
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File upload failed" + e.getMessage());
        }
    }

    @Override
    public void renameFile(FileRenameRequest fileRenameRequest) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(getUserFolderPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getNewName())
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(getUserFolderPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getCurrentName())
                            .build())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File rename failed" + e.getMessage());
        }

        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest();
        fileDeleteRequest.setPath(fileRenameRequest.getPath());
        fileDeleteRequest.setOwner(fileRenameRequest.getOwner());

        deleteFile(fileDeleteRequest);
    }

    @Override
    public List<AppFileDto> getUserFiles(String email, String folder) {
        Iterable<Result<Item>> results =
                minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .prefix(getUserFolderPrefix(email) + folder)
                        .recursive(false)
                        .build());

        List<AppFileDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                AppFileDto appFileDto = new AppFileDto(
                        email,
                        item.isDir(),
                        item.objectName(),
                        item.objectName().substring(getUserFolderPrefix(email).length())
                );
                files.add(appFileDto);
            } catch (Exception e) {
                throw new FileOperationException("Files get failed" + e.getMessage());
            }
        });

        return files;
    }

    public void deleteFile(FileDeleteRequest fileDeleteRequest) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(getUserFolderPrefix(fileDeleteRequest.getOwner()) + fileDeleteRequest.getPath())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File delete failed" + e.getMessage());
        }
    }

    private String getUserFolderPrefix(String email) {
        return "user-" + userService.getUserIdByEmail(email) + "-files/";
    }

}
