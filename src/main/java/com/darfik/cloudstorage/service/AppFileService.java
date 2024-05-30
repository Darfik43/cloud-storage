package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.dto.FileDeleteRequest;
import com.darfik.cloudstorage.dto.FileRenameRequest;
import com.darfik.cloudstorage.dto.FileUploadRequest;

import java.util.List;

public interface AppFileService {

    void uploadFile(FileUploadRequest fileUploadRequest);

    void renameFile(FileRenameRequest fileRenameRequest);

    void deleteFile(FileDeleteRequest fileDeleteRequest);

    List<AppFileDto> getUserFiles(String email, String folder, boolean recursive);
}
