package com.darfik.cloudstorage.domain.s3storage.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public abstract class FileRequest {

    private String owner;

}
