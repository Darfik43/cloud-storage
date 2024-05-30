package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderUploadRequest extends FolderRequest {

    private List<MultipartFile> files;

    public FolderUploadRequest(List<MultipartFile> files, String owner) {
        super(owner);
        this.files = files;
    }

}
