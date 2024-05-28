package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.FolderUploadRequest;

public interface AppFolderService {

    void uploadFolder(FolderUploadRequest folderUploadRequest);

}
