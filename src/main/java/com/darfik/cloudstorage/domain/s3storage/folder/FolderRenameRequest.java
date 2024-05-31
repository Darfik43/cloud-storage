package com.darfik.cloudstorage.domain.s3storage.folder;

import jakarta.validation.constraints.NotBlank;

public record FolderRenameRequest(

        @NotBlank(message = "Current name can't be blank")
        String currentName,

        @NotBlank(message = "New name can't be blank")
        String newName

) {
}
