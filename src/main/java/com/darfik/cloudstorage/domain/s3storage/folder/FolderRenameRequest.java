package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FolderRenameRequest extends FolderRequest {

    private String currentName;
    private String newName;

}
