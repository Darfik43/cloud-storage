package com.darfik.cloudstorage.domain.s3storage.file;

import java.util.List;

public interface S3FileService {

    void uploadFile(FileUploadRequest fileUploadRequest, String owner);

    void renameFile(FileRenameRequest fileRenameRequest, String owner);

    void deleteFile(FileDeleteRequest fileDeleteRequest, String owner);

    List<FileDto> getUserFiles(String owner, String folder, boolean recursive);
}
