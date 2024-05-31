package com.darfik.cloudstorage.domain.s3storage.file;

import java.util.List;

public interface SearchService {

    List<FileDto> searchFiles(SearchRequest searchRequest, String owner);

}
