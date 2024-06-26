package com.darfik.cloudstorage.domain.s3storage.file.dto;

import jakarta.validation.constraints.NotBlank;

public record SearchRequest(

        @NotBlank(message = "Search request can't be null")
        String query

) {
}
