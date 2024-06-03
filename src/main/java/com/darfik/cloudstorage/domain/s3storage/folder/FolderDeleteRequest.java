package com.darfik.cloudstorage.domain.s3storage.folder;

import jakarta.validation.constraints.NotBlank;

public record FolderDeleteRequest(

        String path,

        @NotBlank(message = "Can't delete nothing")
        String name

) {
}
