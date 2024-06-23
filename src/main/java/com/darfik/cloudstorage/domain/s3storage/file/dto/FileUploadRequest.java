package com.darfik.cloudstorage.domain.s3storage.file.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(

        @NotNull(message = "You can't upload nothing")
        MultipartFile file

) {
}
