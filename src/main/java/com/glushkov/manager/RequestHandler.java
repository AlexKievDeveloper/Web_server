package com.glushkov.manager;

import com.glushkov.entities.Request;
import com.glushkov.performers.RequestParser;
import com.glushkov.performers.ResourceReader;
import com.glushkov.performers.ResponseWriter;

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

    public void handler() {
        try {
            try {
                Request parsedRequest = requestParser.parseRequest(socketReader);
                try {
                    ResourceReader resourceReader = new ResourceReader(webAppPath);
                    String content = resourceReader.getResource(parsedRequest.getUri());
                    ResponseWriter.writeSuccessResponse(socketWriter, content);
                } catch (Exception exception) {
                    ResponseWriter.writeNotfoundResponse(socketWriter);
                    exception.printStackTrace();
                }
            } catch (Exception exception) {
                ResponseWriter.writeBadRequestResponse(socketWriter);
                exception.printStackTrace();
            }
        } catch (Exception exception) {
            ResponseWriter.writeInternalServerErrorResponse(socketWriter);
            exception.printStackTrace();
        }
    }
}
