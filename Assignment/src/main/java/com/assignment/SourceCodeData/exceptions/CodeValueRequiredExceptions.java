package com.assignment.SourceCodeData.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeValueRequiredExceptions extends RuntimeException{

    public CodeValueRequiredExceptions(String msg){
        super(msg);
    }
}
