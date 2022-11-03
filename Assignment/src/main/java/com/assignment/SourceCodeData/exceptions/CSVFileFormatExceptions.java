package com.assignment.SourceCodeData.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CSVFileFormatExceptions extends RuntimeException{

    public CSVFileFormatExceptions(String msg){
        super(msg);
    }
}
