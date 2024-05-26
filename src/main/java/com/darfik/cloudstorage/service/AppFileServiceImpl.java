package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.FileUploadRequest;
import com.darfik.cloudstorage.exception.FileUploadException;
import com.darfik.cloudstorage.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AppFileServiceImpl implements AppFileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public void uploadFile(FileUploadRequest fileUploadRequest) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed" + e.getMessage());
        }

        MultipartFile multipartFile = fileUploadRequest.getFile();

        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null) {
            throw new FileUploadException("File must have name");
        }
        String fileName = generateFileName(fileUploadRequest);
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed" + e.getMessage());
        }
        saveFile(inputStream, fileName);
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(FileUploadRequest fileUploadRequest) {
        return "user-" + fileUploadRequest.getOwner() + "-files/" + fileUploadRequest.getFile().getOriginalFilename();
    }

    @SneakyThrows
    private void saveFile(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

}
