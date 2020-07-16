package com.glushkov.server;

import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;
import com.glushkov.io.ResourceReader;
import com.glushkov.util.RequestParser;
import com.glushkov.util.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;

public class RequestHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BufferedReader socketReader;
    private BufferedOutputStream socketWriter;
    private String webAppPath;

    public RequestHandler(BufferedReader socketReader, BufferedOutputStream socketWriter, String webAppPath) {
        this.socketReader = socketReader;
        this.socketWriter = socketWriter;
        this.webAppPath = webAppPath;
    }

    private final RequestParser requestParser = new RequestParser();

    public void handle() {
        logger.debug("Start of request processing");
        ResponseWriter responseWriter = new ResponseWriter(socketWriter);
        try {
            Request parsedRequest = requestParser.parseRequest(socketReader);
            InputStream contentInputStream = ResourceReader.readContent(webAppPath, parsedRequest.getUri());
            responseWriter.writeResponse(HttpStatus.OK, contentInputStream);
        } catch (ServerException serverException) {
            serverException.printStackTrace();
            responseWriter.writeResponse(serverException.getHttpStatus());
        } catch (Exception exception) {
            logger.error("Exception while handle(): ", exception);
            responseWriter.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.debug("Finish of request processing");
    }
}
