package com.darfik.cloudstorage.domain.s3storage.file;

import java.util.List;

public interface S3FileService {

    void uploadFile(FileUploadRequest fileUploadRequest);

    void renameFile(FileRenameRequest fileRenameRequest);

    void deleteFile(FileDeleteRequest fileDeleteRequest);

    List<FileDto> getUserFiles(String email, String folder, boolean recursive);
}
