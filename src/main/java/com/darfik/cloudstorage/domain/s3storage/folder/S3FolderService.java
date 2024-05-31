package com.darfik.cloudstorage.domain.s3storage.folder;

public interface S3FolderService {

    void uploadFolder(FolderUploadRequest folderUploadRequest);

    void renameFolder(FolderRenameRequest folderRenameRequest);

}
