package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderRenameRequest extends FolderRequest {

    private String currentName;
    private String newName;

    public FolderRenameRequest(String currentName, String newName,
                               String owner) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
    }

}
