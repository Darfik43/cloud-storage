package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record FileDeleteRequest(
        @NotNull(message = "Failed while getting file path") String path) {
}
