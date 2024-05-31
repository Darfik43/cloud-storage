package com.darfik.cloudstorage.domain.s3storage.folder;

public interface S3FolderService {

    void uploadFolder(FolderUploadRequest folderUploadRequest, String owner);

    void renameFolder(FolderRenameRequest folderRenameRequest, String owner);

}
