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
    public List<FileResponse> searchFiles(SearchRequest searchRequest, String owner) {
        List<FileResponse> files =
                s3FileService.getAllUserFiles(owner, "");
        return listBySearchTerm(files, searchRequest.query());
    }

    private List<FileResponse> listBySearchTerm(List<FileResponse> files,
                                                String query) {
        String lowerCaseSearchTerm = query.toLowerCase();
        return files.stream()
                .filter(file -> (file.path() + file.name()).toLowerCase().contains(lowerCaseSearchTerm))
                .collect(Collectors.toList());
    }
}
