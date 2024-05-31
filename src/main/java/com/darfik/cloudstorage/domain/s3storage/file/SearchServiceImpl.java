package com.darfik.cloudstorage.domain.s3storage.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final S3FileService s3FileService;

    @Override
    public List<FileDto> searchFiles(SearchRequest searchRequest, String owner) {
        List<FileDto> files =
                s3FileService.getUserFiles(owner, "", true);
        return listBySearchTerm(files, searchRequest.searchTerm());
    }

    private List<FileDto> listBySearchTerm(List<FileDto> files,
                                           String searchTerm) {
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        return files.stream()
                .filter(file -> (file.getPath() + file.getName()).toLowerCase().contains(lowerCaseSearchTerm))
                .collect(Collectors.toList());
    }
}
