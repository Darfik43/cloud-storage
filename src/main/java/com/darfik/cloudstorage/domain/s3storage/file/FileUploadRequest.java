package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
