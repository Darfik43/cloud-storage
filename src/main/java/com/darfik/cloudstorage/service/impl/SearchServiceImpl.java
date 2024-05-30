package com.darfik.cloudstorage.service.impl;

import com.darfik.cloudstorage.dto.AppFileDto;
import com.darfik.cloudstorage.dto.SearchRequest;
import com.darfik.cloudstorage.service.AppFileService;
import com.darfik.cloudstorage.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final AppFileService appFileService;

    @Override
    public List<AppFileDto> searchFiles(SearchRequest searchRequest) {
        List<AppFileDto> files =
                appFileService.getUserFiles(searchRequest.getOwner(), "", true);
        return listBySearchTerm(files, searchRequest.getSearchTerm());
    }

    private List<AppFileDto> listBySearchTerm(List<AppFileDto> files,
                                              String searchTerm) {
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        return files.stream()
                .filter(file -> (file.getPath() + file.getName()).toLowerCase().contains(lowerCaseSearchTerm))
                .collect(Collectors.toList());
    }
}
