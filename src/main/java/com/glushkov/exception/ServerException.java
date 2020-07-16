package com.glushkov.exception;

import com.glushkov.entity.HttpStatus;

public class ServerException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public ServerException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
        System.out.println(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
