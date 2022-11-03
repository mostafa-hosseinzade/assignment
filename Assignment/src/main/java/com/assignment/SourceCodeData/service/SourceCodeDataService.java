package com.assignment.SourceCodeData.service;

import com.assignment.SourceCodeData.dao.ISourceCodeDataRepository;
import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import com.assignment.SourceCodeData.exceptions.CSVFileFormatExceptions;
import com.assignment.SourceCodeData.exceptions.CodeValueRequiredExceptions;
import com.assignment.SourceCodeData.exceptions.SourceCodeDataNotFoundExceptions;
import com.assignment.SourceCodeData.exceptions.UniqueCodeExceptions;
import com.assignment.SourceCodeData.model.SourceCodeData;
import com.assignment.SourceCodeData.utils.CSVHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SourceCodeDataService implements ISourceCodeDataService{

    private final ISourceCodeDataRepository iSourceCodeDataRepository;
    private final CSVHelper csvHelper;

    public List<SourceCodeDataDto> findAll(){
        List<SourceCodeDataDto> sourceCodeDataDtosResult = new LinkedList<>();
        Iterable<SourceCodeData> sourceCodeDataIterable = iSourceCodeDataRepository.findAll();
        sourceCodeDataIterable.forEach(sourceCodeData -> {
            sourceCodeDataDtosResult.add(sourceCodeData.getTransferObject());
        });
        return sourceCodeDataDtosResult;
    }

    public SourceCodeDataDto findByCode(String code){
        Optional<SourceCodeData> sourceCodeData = iSourceCodeDataRepository.findByCode(code);
        if(sourceCodeData.isPresent()){
            return sourceCodeData.get().getTransferObject();
        }
        return null;
    }

    public Boolean deleteAll(){
        try {
            iSourceCodeDataRepository.deleteAll();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public void saveAllWithUploadedCsvFile(MultipartFile file) {
        try {
            if(csvHelper.hasCSVFormat(file)){
                throw new CSVFileFormatExceptions("Please upload a csv file!");
            }
            List<SourceCodeDataDto> sourceCodeDataDtos = csvHelper.csvToSourceCodeDataDtos(file.getInputStream());
            if(!uniqueCode(sourceCodeDataDtos)){
                throw new UniqueCodeExceptions("Code should be unique");
            }
            sourceCodeDataDtos.forEach(this::save);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    private Boolean uniqueCode(List<SourceCodeDataDto> sourceCodeDataDtos){
        List<SourceCodeDataDto> duplicateDataList = sourceCodeDataDtos.stream()
                .distinct()
                .filter(entry -> Collections.frequency(sourceCodeDataDtos, entry) > 1).toList();
        if(duplicateDataList.size() == 0){
            return true;
        }
        return false;
    }

    public Long save(SourceCodeDataDto sourceCodeDataDto){
        if(sourceCodeDataDto.getCode() == null || sourceCodeDataDto.getCode().isEmpty()){
           throw new CodeValueRequiredExceptions("code value can not be null!!!");
        }
        if(findByCode(sourceCodeDataDto.getCode()) != null){
            throw new UniqueCodeExceptions("code value is unique, you put repeated code("+sourceCodeDataDto.getCode()+") which saved before!!");
        }
        SourceCodeData sourceCodeData =iSourceCodeDataRepository.save(new SourceCodeData().adaptTransferToDomainObject(sourceCodeDataDto));
        return sourceCodeData.getId();
    }

}
