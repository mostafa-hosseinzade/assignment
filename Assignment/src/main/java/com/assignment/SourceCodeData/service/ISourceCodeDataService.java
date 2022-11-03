package com.assignment.SourceCodeData.service;

import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISourceCodeDataService {

    List<SourceCodeDataDto> findAll();

    SourceCodeDataDto findByCode(String code);

    Boolean deleteAll();

    void saveAllWithUploadedCsvFile(MultipartFile file);

    Long save(SourceCodeDataDto sourceCodeDataDto);
}
