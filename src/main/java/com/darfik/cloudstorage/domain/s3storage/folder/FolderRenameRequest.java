package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FolderRenameRequest extends FolderRequest {

    private String currentName;
    private String newName;

    public FolderRenameRequest(String currentName, String newName) {
        super();
        this.currentName = currentName;
        this.newName = newName;
    }

}
