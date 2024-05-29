package com.darfik.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
