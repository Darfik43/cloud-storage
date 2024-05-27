package com.darfik.cloudstorage.dto;

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
public class AppFileDto {

    private String owner;
    private boolean isDir;
    private String path;
    private String name;

}
