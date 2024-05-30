package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.s3storage.MinioProperties;
import com.darfik.cloudstorage.domain.user.UserServiceImpl;
import com.darfik.cloudstorage.domain.exception.FileOperationException;
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
    public List<AppFileDto> getUserFiles(String email, String folder, boolean recursive) {
        Iterable<Result<Item>> results =
                minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .prefix(getUserFolderPrefix(email) + folder)
                        .recursive(recursive)
                        .build());

        List<AppFileDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                String path = getPath(item.objectName(), email);
                String name = getFileName(item.objectName(), email);
                AppFileDto appFileDto = new AppFileDto(
                        email,
                        item.isDir(),
                        path,
                        name
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

    private String getPath(String name, String email) {
        return name.substring(getUserFolderPrefix(email).length(), name.lastIndexOf("/") + 1);
    }

    private String getFileName(String path, String email) {
        return path.endsWith("/") ? path.substring(getUserFolderPrefix(email).length(), path.lastIndexOf("/") + 1) : path.substring(path.lastIndexOf("/") + 1);
    }
}
