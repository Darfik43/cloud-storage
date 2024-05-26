package com.darfik.cloudstorage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class FileRequest {

    @NotNull(message = "Owner's name is null")
    private String owner;

}
