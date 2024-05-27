package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.*;
import com.darfik.cloudstorage.exception.FileOperationException;
import com.darfik.cloudstorage.service.props.MinioProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

        String fileName = generateUserPrefix(fileUploadRequest.getOwner()) + fileUploadRequest.getFile().getOriginalFilename();
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
                    .object(generateUserPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getNewName())
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(generateUserPrefix(fileRenameRequest.getOwner()) + fileRenameRequest.getCurrentName())
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
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .prefix(generateUserPrefix(email))
                    .recursive(false)
                    .build());

        List<AppFileDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                AppFileDto appFileDto = new AppFileDto(
                        email,
                        item.isDir(),
                        item.objectName().replaceAll(generateUserPrefix(email), ""),
                        item.objectName()
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
                    .object(generateUserPrefix(fileDeleteRequest.getOwner()) + fileDeleteRequest.getPath())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File delete failed" + e.getMessage());
        }
    }

    private String generateUserPrefix(String email) {
        return "user-" + userService.getByEmail(email).getId() + "-files/";
    }

}
