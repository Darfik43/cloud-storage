package com.darfik.cloudstorage.domain.s3storage.folder;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public record FolderUploadRequest(List<MultipartFile> files) {
}
