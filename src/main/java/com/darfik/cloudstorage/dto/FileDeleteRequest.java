package com.darfik.cloudstorage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteRequest extends FileRequest {

    @NotNull(message = "Failed while getting file path")
    private String path;

    public FileDeleteRequest(String path, String owner) {
        super(owner);
        this.path = path;
    }

}
