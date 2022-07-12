package com.medhead.ers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NHSSpecIntrouvableException extends RuntimeException {
    public NHSSpecIntrouvableException(String s) {
        super(s);
    }
}
