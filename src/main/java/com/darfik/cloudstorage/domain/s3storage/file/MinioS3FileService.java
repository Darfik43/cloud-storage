package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
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

        String fileName =
                getUserFolderPrefix(owner) + fileUploadRequest.file().getOriginalFilename();
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
    public void renameFile(FileRenameRequest fileRenameRequest, String owner) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(getUserFolderPrefix(owner + fileRenameRequest.newName()))
                    .source(CopySource.builder()
                            .bucket(minioProperties.getBucket())
                            .object(getUserFolderPrefix(owner + fileRenameRequest.currentName()))
                            .build())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File rename failed" + e.getMessage());
        }

        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest(fileRenameRequest.path());

        deleteFile(fileDeleteRequest, owner);
    }

    @Override
    public List<FileDto> getUserFiles(String email, String folder,
                                      boolean recursive) {
        Iterable<Result<Item>> results =
                minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .prefix(getUserFolderPrefix(email) + folder)
                        .recursive(recursive)
                        .build());

        List<FileDto> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                String path = folder;
                String name =
                        item.objectName().substring((getUserFolderPrefix(email) + folder).length());
                FileDto fileDto = new FileDto(
                        email,
                        item.isDir(),
                        path,
                        name
                );
                files.add(fileDto);
            } catch (Exception e) {
                throw new FileOperationException("Files get failed" + e.getMessage());
            }
        });

        return files;
    }

    @Override
    public void deleteFile(FileDeleteRequest fileDeleteRequest, String owner) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(getUserFolderPrefix(owner) + fileDeleteRequest.path())
                    .build());
        } catch (Exception e) {
            throw new FileOperationException("File delete failed" + e.getMessage());
        }
    }

    private String getUserFolderPrefix(String email) {
        return "user-" + userService.getUserIdByEmail(email) + "-files/";
    }

    private String getPath(String name, String email) {
        return name.substring(getUserFolderPrefix(email).length(),
                name.lastIndexOf("/") + 1);
    }

    private String getFileName(String path, String email) {
        return path.endsWith("/") ?
                path.substring(getUserFolderPrefix(email).length(),
                        path.lastIndexOf("/") + 1) :
                path.substring(path.lastIndexOf("/") + 1);
    }
}
