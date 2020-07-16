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
            logger.error("Error while response was writing");
            throw new ServerException("Error while response was writing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void writeResponse(HttpStatus httpStatus, InputStream inputStream) {

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {

            socketWriter.write(httpStatus.getStatusLine().getBytes());
            socketWriter.write(LINE_END.getBytes());
            socketWriter.write(LINE_END.getBytes());
            int c;
            while ((c = bufferedInputStream.read()) != -1) {
                socketWriter.write(c);
            }
            logger.debug("Server answered: {}", httpStatus.getStatusLine());
        } catch (IOException ioException) {
            logger.error("Error while response was writing");
            throw new ServerException("Error while response was writing", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}