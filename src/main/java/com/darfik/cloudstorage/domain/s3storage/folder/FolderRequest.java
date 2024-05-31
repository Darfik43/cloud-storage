package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
public class FolderRequest {

    private String owner;

    public FolderRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            this.owner = auth.getName();
        }
    }

}
