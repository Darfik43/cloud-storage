package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NotBlank
@Getter
@Setter
@EqualsAndHashCode
public class FileDto {

    private String owner;
    private boolean isDir;
    private String path;
    private String name;

}
