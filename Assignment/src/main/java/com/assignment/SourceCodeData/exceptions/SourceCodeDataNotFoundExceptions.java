package com.assignment.SourceCodeData.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SourceCodeDataNotFoundExceptions extends RuntimeException{

    public SourceCodeDataNotFoundExceptions(String msg){
        super(msg);
    }
}
