package com.ams.building.server.exception;

public class ApiError {

    private Integer statusCode;
    private String message;

    public ApiError(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiError() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
