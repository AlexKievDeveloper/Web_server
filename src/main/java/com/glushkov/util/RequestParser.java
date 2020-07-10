package com.glushkov.util;

import ch.qos.logback.classic.Logger;
import com.glushkov.entity.HttpMethod;
import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(RequestParser.class);

    public Request parseRequest(BufferedReader socketReader) {
        try {
            String requestLine = socketReader.readLine();
            logger.debug("Request was: " + requestLine);

            if (requestLine == null || requestLine.isEmpty()) {
                throw new ServerException("Request line was null or empty:" + requestLine, HttpStatus.BAD_REQUEST);
            }
            Request request = new Request();
            injectUrlAndHttpMethod(request, requestLine);
            injectHeaders(request, socketReader);
            return request;

        } catch (IOException ioException) {
            logger.error("IOException while parsing request: ", ioException);
            throw new ServerException("IOException while parsing request: " + ioException, HttpStatus.BAD_REQUEST);
        }
    }

    void injectUrlAndHttpMethod(Request request, String requestLine) {
        String[] split = requestLine.split(" ");

        if (!split[0].equals(HttpMethod.GET.toString()) && !split[0].equals(HttpMethod.POST.toString())
                && !split[0].equals(HttpMethod.PUT.toString()) && !split[0].equals(HttpMethod.DELETE.toString())) {
            logger.info("Server exception while injecting url and http method, method was not valid:" + split[0]);
            throw new ServerException("Server exception while injecting url and http method, method was not valid:" + split[0],
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
        request.setHttpMethod(HttpMethod.valueOf(split[0]));
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
                logger.debug(header);
            }
            request.setHeaders(headers);
        } catch (IOException ioException) {
            logger.error("IOException while injecting headers " + ioException + "Server answer: "
                    + HttpStatus.BAD_REQUEST, ioException);
            throw new ServerException("IOException while injecting headers " + ioException, HttpStatus.BAD_REQUEST);
        }
    }
}
