package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.s3storage.file.dto.FileDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.file.dto.FileRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.file.dto.FileResponse;
import com.darfik.cloudstorage.domain.s3storage.file.dto.FileUploadRequest;

import java.util.List;

public interface S3FileService {

    void uploadFile(FileUploadRequest fileUploadRequest, String owner);

    void renameFile(FileRenameRequest fileRenameRequest, String owner);

    void deleteFile(FileDeleteRequest fileDeleteRequest, String owner);

    List<FileResponse> getUserFiles(String owner, String path);

    List<FileResponse> getAllUserFiles(String owner, String path);
}
