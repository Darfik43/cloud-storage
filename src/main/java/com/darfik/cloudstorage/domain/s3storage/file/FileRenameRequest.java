package com.darfik.cloudstorage.domain.s3storage.file;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileRenameRequest extends FileRequest {

    @NotBlank(message = "Current name can't be blank")
    private String currentName;

    @NotBlank(message = "New name can't be blank")
    private String newName;

    @NotBlank(message = "Can't get the path of the file")
    private String path;

    public FileRenameRequest(String currentName, String newName, String path,
                             String owner) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }

}
