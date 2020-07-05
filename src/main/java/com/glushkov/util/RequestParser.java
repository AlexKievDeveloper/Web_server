package com.glushkov.util;

import com.glushkov.entity.HttpMethod;
import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public Request parseRequest(BufferedReader socketReader) {
        try {
            String requestLine = socketReader.readLine();

            if (requestLine != null && !requestLine.isEmpty()) {
                Request request = new Request();
                injectUrlAndHttpMethod(request, requestLine);
                injectHeaders(request, socketReader);
                return request;
            } else {
                throw new ServerException(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException ioException) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
    }

    void injectUrlAndHttpMethod(Request request, String requestLine) {
        String[] split = requestLine.split(" ");

        if (HttpMethod.getByName(split[0]) instanceof HttpMethod) {
            request.setHttpMethod(HttpMethod.valueOf(split[0]));
            request.setUri(split[1]);
        }
    }

    void injectHeaders(Request request, BufferedReader socketReader) {

        Map<String, String> headers = new HashMap<>();

        String header;
        try {
            while (!(header = socketReader.readLine()).isEmpty()) {
                String[] splitHeader = header.split(" ");
                String headerName = splitHeader[0];
                String headerValue = splitHeader[1];
                headers.put(headerName, headerValue);
            }
            request.setHeaders(headers);
        } catch (IOException ioException) {
            throw new ServerException(HttpStatus.BAD_REQUEST);
        }
    }
}
