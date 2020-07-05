package com.glushkov.util;

import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {

    private static final String LINE_END = "\r\n";

    BufferedWriter socketWriter;

    public ResponseWriter(BufferedWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void writeResponse(HttpStatus httpStatus, String body) {
        try {
            socketWriter.write(httpStatus.getStatusLine());
            socketWriter.write(LINE_END);
            socketWriter.write((LINE_END));
            if (body != null) {
                socketWriter.write(body);
            }
            socketWriter.flush();
        } catch (IOException ioException) {
            throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}