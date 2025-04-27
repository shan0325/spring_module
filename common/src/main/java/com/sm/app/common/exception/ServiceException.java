package com.sm.app.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final ExceptionResponse exceptionResponse;

    public ServiceException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getMessage());
        this.exceptionResponse = exceptionResponse;
    }

    public ServiceException(String message) {
        this(ExceptionResponse.builder(message).build());
    }
}
