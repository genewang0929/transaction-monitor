package com.chunhanwang.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
