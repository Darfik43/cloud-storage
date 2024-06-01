package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotBlank;

public record FileDeleteRequest(

        @NotBlank(message = "Failed while getting file path")
        String path,

        @NotBlank(message = "Can't delete nothing")
        String name

) {
}
