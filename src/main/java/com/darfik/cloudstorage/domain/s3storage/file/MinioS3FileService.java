package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
import com.darfik.cloudstorage.domain.s3storage.util.UserFolderResolver;
import com.darfik.cloudstorage.domain.user.UserServiceImpl;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public List<FileResponse> getAllUserFiles(String owner, String path) {
        return convertResultToDto(getListObjects(owner, path, true), owner);
    }


    @Override
    public List<FileResponse> getUserFiles(String owner, String path) {
        return convertResultToDto(getListObjects(owner, path, false), owner);
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

    private List<FileResponse> convertResultToDto(Iterable<Result<Item>> results, String owner) {
        List<FileResponse> files = new ArrayList<>();

        results.forEach(result -> {
            try {
                Item item = result.get();
                String path = deleteFileNameFromPath(item.objectName().substring(getUserFolderPrefix(owner).length()));
                FileResponse fileResponse = new FileResponse(
                        owner,
                        item.isDir(),
                        path,
                        item.objectName().substring((getUserFolderPrefix(owner) + path).length())
                );
                files.add(fileResponse);
            } catch (Exception e) {
                throw new FileOperationException("Can't get a list of your files: " + e.getMessage());
            }
        });

        return files;
    }

    private Iterable<Result<Item>> getListObjects(String owner, String path,
                                                  boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioProperties.getBucket())
                .prefix(getUserFolderPrefix(owner) + path)
                .recursive(recursive)
                .build());
    }

    private String getUserFolderPrefix(String owner) {
         return UserFolderResolver.getUserFolderPrefix(userService.getUserIdByEmail(owner));
    }

    private String deleteFileNameFromPath(String path)  {

        if ((path.lastIndexOf("/") + 1) < path.length()) {
            return path.substring(0, path.lastIndexOf("/") + 1);
        } else {
            String trimmedPath = path.substring(0, path.length() - 1);
            return trimmedPath.substring(0, trimmedPath.lastIndexOf("/") + 1);
            }
    }
}
