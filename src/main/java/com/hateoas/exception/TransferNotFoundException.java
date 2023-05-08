package com.hateoas.exception;

import org.springframework.http.HttpStatus;

public class TransferNotFoundException extends RuntimeException{

    private HttpStatus httpStatus;

    public TransferNotFoundException(String message) {
        super(message);
    }

    public TransferNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferNotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public TransferNotFoundException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}