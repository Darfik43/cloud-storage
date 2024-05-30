package com.darfik.cloudstorage.domain.s3storage.folder;

public interface AppFolderService {

    void uploadFolder(FolderUploadRequest folderUploadRequest);

    void renameFolder(FolderRenameRequest folderRenameRequest);

}
