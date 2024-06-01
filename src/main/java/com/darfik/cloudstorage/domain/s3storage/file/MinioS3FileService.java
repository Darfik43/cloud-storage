package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
import com.darfik.cloudstorage.domain.s3storage.util.UserFolderResolver;
import com.darfik.cloudstorage.domain.user.UserServiceImpl;
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
public class MinioS3FileService implements S3FileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final UserServiceImpl userService;

    @Override
    public void uploadFile(FileUploadRequest fileUploadRequest, String owner) {
        MultipartFile multipartFile = fileUploadRequest.file();
        String fileName = getUserFolderPrefix(owner) + fileUploadRequest.file().getOriginalFilename();

        putObject(multipartFile, fileName);
    }

    @Override
    public void deleteFile(FileDeleteRequest fileDeleteRequest, String owner) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(getUserFolderPrefix(owner) + fileDeleteRequest.path() + fileDeleteRequest.name())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File delete failed" + e.getMessage());
        }
    }

    @Override
    public void renameFile(FileRenameRequest fileRenameRequest, String owner) {
        copyObject(fileRenameRequest, owner);

        deleteFile(new FileDeleteRequest(fileRenameRequest.path(), fileRenameRequest.currentName()), owner);
    }


    @Override
    public List<FileResponse> getUserFiles(String email, String path, boolean recursive) {
        Iterable<Result<Item>> results = getListObjects(email, path, recursive);

        return convertResultToDto(results, email, path);
    }

    private void copyObject(FileRenameRequest fileRenameRequest, String owner) {
        String userFolderPrefix = getUserFolderPrefix(owner);

        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(userFolderPrefix + fileRenameRequest.path() + fileRenameRequest.newName())
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(userFolderPrefix + fileRenameRequest.path() + fileRenameRequest.currentName())
                            .build())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File rename failed: " + e.getMessage());
        }
    }

    private void putObject(MultipartFile multipartFile, String fileName) {
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File upload failed: " + e.getMessage());
        }
    }

    private List<FileResponse> convertResultToDto(Iterable<Result<Item>> results, String email, String path) {
        List<FileResponse> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                FileResponse fileResponse = new FileResponse(
                        email,
                        item.isDir(),
                        path,
                        item.objectName().substring((getUserFolderPrefix(email) + path).length())
                );
                files.add(fileResponse);
            } catch (Exception e) {
                throw new FileOperationException("Can't get a list of your files: " + e.getMessage());
            }
        });

        return files;
    }

    private Iterable<Result<Item>> getListObjects(String email, String path,
                                                  boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .prefix(getUserFolderPrefix(email) + path)
                .recursive(recursive)
                .build());
    }

    private String getUserFolderPrefix(String owner) {
         return UserFolderResolver.getUserFolderPrefix(userService.getUserIdByEmail(owner));
    }

}
