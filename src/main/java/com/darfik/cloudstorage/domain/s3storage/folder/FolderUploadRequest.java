package com.darfik.cloudstorage.domain.s3storage.folder;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public record FolderUploadRequest(

        @NotBlank(message = "Folder can't be null")
        List<MultipartFile> files

) {
}
