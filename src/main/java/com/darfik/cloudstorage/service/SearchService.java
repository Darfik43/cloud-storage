package com.darfik.cloudstorage.service;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.dto.SearchRequest;

import java.util.List;

public interface SearchService {

    List<AppFileDto> searchFiles(SearchRequest searchRequest);

}
