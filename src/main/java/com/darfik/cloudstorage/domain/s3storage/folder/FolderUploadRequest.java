package com.darfik.cloudstorage.domain.s3storage.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FolderUploadRequest extends FolderRequest {

    private List<MultipartFile> files;

}
