package com.glushkov.util;

import ch.qos.logback.classic.Logger;
import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ResponseWriter.class);

    private static final String EMPTY_CONTENT = "";

    private static final String LINE_END = "\r\n";

    private BufferedWriter socketWriter;

    public ResponseWriter(BufferedWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void writeResponse(HttpStatus httpStatus) {
        writeResponse(httpStatus, EMPTY_CONTENT);
    }

    public void writeResponse(HttpStatus httpStatus, String body) {
        try {
            socketWriter.write(httpStatus.getStatusLine());
            socketWriter.write(LINE_END);
            socketWriter.write(LINE_END);
            socketWriter.write(body);
            logger.debug("Server answered: " + httpStatus.getStatusLine());
        } catch (IOException ioException) {
            logger.error("IOException while response was writing  ", ioException);
            throw new ServerException("IOException while response was writing  " + ioException, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}