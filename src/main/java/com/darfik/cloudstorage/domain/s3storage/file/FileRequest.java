package com.darfik.cloudstorage.domain.s3storage.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
@Getter
@Setter
public abstract class FileRequest {

    private String owner;

    public FileRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            this.owner = auth.getName();
        }
    }
}
