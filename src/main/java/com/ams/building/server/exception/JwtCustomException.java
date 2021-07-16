package com.ams.building.server.exception;

import org.springframework.http.HttpStatus;

public class JwtCustomException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public JwtCustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
