package com.assignment.SourceCodeData.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class CSVHelper {
    public static String TYPE = "text/csv";

    public boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public List<SourceCodeDataDto> csvToSourceCodeDataDtos(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<SourceCodeDataDto> sourceCodeDataDtos = new ArrayList<SourceCodeDataDto>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                SourceCodeDataDto sourceCodeDataDto =SourceCodeDataDto.builder()
                        .source(csvRecord.get("source"))
                        .codeListCode(csvRecord.get("codeListCode"))
                        .code(csvRecord.get("code"))
                        .displayValue(csvRecord.get("displayValue"))
                        .longDescription(csvRecord.get("longDescription"))
                        .fromDate(fetchDateFromString(csvRecord.get("fromDate")))
                        .toDate(fetchDateFromString(csvRecord.get("toDate")))
                        .sortingPriority(fetchSortingPriority(csvRecord.get("sortingPriority")))
                        .build();


                sourceCodeDataDtos.add(sourceCodeDataDto);
            }

            return sourceCodeDataDtos;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private Integer fetchSortingPriority(String inputSortingPriority){
        try{
            if(inputSortingPriority == null || inputSortingPriority.isEmpty()){
                return null;
            }
            return Integer.valueOf(inputSortingPriority);
        }catch (Exception e){
            return null;
        }
    }

    private Date fetchDateFromString(String inputDate)  {
        try {
            if(inputDate == null || inputDate.isEmpty()){
                return null;
            }
            return new SimpleDateFormat("dd-MM-yyyy").parse(inputDate);
        }catch (Exception e){
            return null;
        }
    }
}