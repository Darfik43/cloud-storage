package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
public record FileRenameRequest(
        @NotBlank(message = "Current name can't be blank") String currentName,
        @NotBlank(message = "New name can't be blank") String newName,
        @NotBlank(message = "Can't get the path of the file") String path) {

}
