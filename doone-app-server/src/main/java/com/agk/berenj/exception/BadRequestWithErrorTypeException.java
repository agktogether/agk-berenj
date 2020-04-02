package com.agk.berenj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestWithErrorTypeException extends RuntimeException {

    public BadRequestWithErrorTypeException(String message) {
        super(message);
    }

    public BadRequestWithErrorTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
