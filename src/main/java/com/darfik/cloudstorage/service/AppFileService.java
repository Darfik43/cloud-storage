package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.FileUploadRequest;

public interface AppFileService {
    void uploadFile(FileUploadRequest fileUploadRequest);
}
