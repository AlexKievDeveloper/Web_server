package com.glushkov.performers;

import com.glushkov.entities.HttpMethod;
import com.glushkov.entities.Request;

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
                throw new RuntimeException("Client request was empty string or null:" + requestLine);
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    void injectUrlAndHttpMethod(Request request, String requestLine) {
        String[] split = requestLine.split(" ");

        if (split[0].equals(HttpMethod.GET.toString()) || split[0].equals(HttpMethod.POST.toString())) {
            HttpMethod httpMethod = HttpMethod.valueOf(split[0]);
            request.setHttpMethod(httpMethod);
            request.setUri(split[1]);
        } else {
            throw new RuntimeException("Client request did not contain the correct HttpMethod:" + requestLine);
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
            ioException.printStackTrace();
        }
    }
}
