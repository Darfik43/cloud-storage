package com.darfik.cloudstorage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadRequest extends FileRequest {

    @NotNull(message = "You can't upload nothing")
    private MultipartFile file;

    public FileUploadRequest(String owner, MultipartFile file) {
        super(owner);
        this.file = file;
    }

}
