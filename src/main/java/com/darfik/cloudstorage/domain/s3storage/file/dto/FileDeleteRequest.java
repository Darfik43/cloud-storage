package com.darfik.cloudstorage.domain.s3storage.file.dto;

import jakarta.validation.constraints.NotBlank;

public record FileDeleteRequest(

        String path,

        @NotBlank(message = "Can't delete nothing")
        String name

) {
}
