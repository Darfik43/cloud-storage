package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class FileUploadRequest extends FileRequest {

    @NotNull(message = "You can't upload nothing")
    private MultipartFile file;

    public FileUploadRequest(MultipartFile file, String owner) {
        super(owner);
        this.file = file;
    }

}
