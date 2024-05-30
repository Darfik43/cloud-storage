package com.darfik.cloudstorage.domain.s3storage.file;

import java.util.List;

public interface AppFileService {

    void uploadFile(FileUploadRequest fileUploadRequest);

    void renameFile(FileRenameRequest fileRenameRequest);

    void deleteFile(FileDeleteRequest fileDeleteRequest);

    List<AppFileDto> getUserFiles(String email, String folder, boolean recursive);
}
