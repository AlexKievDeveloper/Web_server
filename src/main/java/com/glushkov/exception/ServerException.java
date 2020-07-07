package com.glushkov.exception;

import com.glushkov.entity.HttpStatus;

public class ServerException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
