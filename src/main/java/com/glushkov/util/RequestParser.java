package com.glushkov.util;

import com.glushkov.entity.HttpMethod;
import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Request parseRequest(BufferedReader socketReader) {
        try {
            String requestLine = socketReader.readLine();
            logger.debug("Request line: {}", requestLine);

            if (requestLine == null || requestLine.isEmpty()) {
                logger.info("Request line is empty or null:" + requestLine);
                throw new ServerException("Request line was null or empty", HttpStatus.NOT_FOUND);
            }

            Request request = new Request();
            injectUrlAndHttpMethod(request, requestLine);
            injectHeaders(request, socketReader);
            return request;
        } catch (IOException ioException) {
            logger.error("Error while parsing request: ", ioException);
            throw new ServerException("Error while parsing request: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void injectUrlAndHttpMethod(Request request, String requestLine) {
        String[] split = requestLine.split(" ");
        HttpMethod httpMethod = HttpMethod.getByNameOrNull(split[0]);

        if (httpMethod == null) {
            logger.info("Error in injectUrlAndHttpMethod, method was not valid: {}", split[0]);
            throw new ServerException("Error in injectUrlAndHttpMethod, method was not valid: " + split[0], HttpStatus.METHOD_NOT_ALLOWED);
        }
        request.setHttpMethod(httpMethod);
        request.setUri(split[1]);
    }

    void injectHeaders(Request request, BufferedReader socketReader) {
        Map<String, String> headers = new HashMap<>();

        try {
            String header;
            while (!(header = socketReader.readLine()).isEmpty()) {
                String[] splitHeader = header.split(" ");
                String headerName = splitHeader[0];
                String headerValue = splitHeader[1];
                headers.put(headerName, headerValue);
                logger.debug("header: {}", header);
            }
            request.setHeaders(headers);
        } catch (IOException ioException) {
            logger.error("Error while injecting headers. Server answer: " + HttpStatus.BAD_REQUEST, ioException);
            throw new ServerException("Error while injecting headers", HttpStatus.BAD_REQUEST);
        }
    }
}
