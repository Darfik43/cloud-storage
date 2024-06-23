package com.darfik.cloudstorage.domain.s3storage.folder.service;

import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderDeleteRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderRenameRequest;
import com.darfik.cloudstorage.domain.s3storage.folder.dto.FolderUploadRequest;

public interface S3FolderService {

    void uploadFolder(FolderUploadRequest folderUploadRequest, String owner);

    void renameFolder(FolderRenameRequest folderRenameRequest, String owner);

    void deleteFolder(FolderDeleteRequest folderDeleteRequest, String owner);

}
