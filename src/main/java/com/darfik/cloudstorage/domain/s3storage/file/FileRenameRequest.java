package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotBlank;

public record FileRenameRequest(

        @NotBlank(message = "Current name can't be blank")
        String currentName,

        @NotBlank(message = "New name can't be blank")
        String newName,

        String path

) {
}
