package com.darfik.cloudstorage.domain.s3storage.file;

import com.darfik.cloudstorage.domain.s3storage.file.dto.FileResponse;
import com.darfik.cloudstorage.domain.s3storage.file.dto.SearchRequest;

import java.util.List;

public interface SearchService {

    List<FileResponse> searchFiles(SearchRequest searchRequest, String owner);

}
