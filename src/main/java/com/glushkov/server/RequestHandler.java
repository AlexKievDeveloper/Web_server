package com.glushkov.server;

import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;
import com.glushkov.io.ResourceReader;
import com.glushkov.util.RequestParser;
import com.glushkov.util.ResponseWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RequestHandler {
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String webAppPath;

    public RequestHandler(BufferedReader socketReader, BufferedWriter socketWriter, String webAppPath) {
        this.socketReader = socketReader;
        this.socketWriter = socketWriter;
        this.webAppPath = webAppPath;
    }

    private final RequestParser requestParser = new RequestParser();

    public void handle() {
        ResponseWriter responseWriter = new ResponseWriter(socketWriter);
        try {
            try {
                Request parsedRequest = requestParser.parseRequest(socketReader);
                String content = ResourceReader.readContent(webAppPath, parsedRequest.getUri());
                responseWriter.writeResponse(HttpStatus.OK, content);
            } catch (ServerException serverException) {
                serverException.printStackTrace();
                responseWriter.writeResponse(serverException.getHttpStatus(), null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            responseWriter.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
