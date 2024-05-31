package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDeleteRequest extends FileRequest {

    @NotNull(message = "Failed while getting file path")
    private String path;

    public FileDeleteRequest(String path) {
        super();
        this.path = path;
    }

}