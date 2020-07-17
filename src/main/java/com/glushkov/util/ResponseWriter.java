package com.glushkov.util;

import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseWriter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String EMPTY_CONTENT = "";

    private static final String LINE_END = "\r\n";

    private BufferedOutputStream socketWriter;

    public ResponseWriter(BufferedOutputStream socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void writeResponse(HttpStatus httpStatus) {

        try {
            socketWriter.write(httpStatus.getStatusLine().getBytes());
            socketWriter.write(LINE_END.getBytes());
            socketWriter.write(LINE_END.getBytes());
            socketWriter.write(EMPTY_CONTENT.getBytes());
        } catch (IOException ioException) {
            logger.error("Error while response was writing", ioException);
            throw new ServerException("Error while response was writing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void writeResponse(HttpStatus httpStatus, InputStream inputStream) {

        try (inputStream) {

            socketWriter.write(httpStatus.getStatusLine().getBytes());
            socketWriter.write(LINE_END.getBytes());
            socketWriter.write(LINE_END.getBytes());

            byte[] buffer = new byte[16384];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                socketWriter.write(buffer, 0, count);
            }
            logger.debug("Server answered: {}", httpStatus.getStatusLine());
        } catch (IOException ioException) {
            logger.error("Error while response was writing", ioException);
            throw new ServerException("Error while response was writing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}