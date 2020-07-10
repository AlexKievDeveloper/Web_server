package com.glushkov.server;

import ch.qos.logback.classic.Logger;
import com.glushkov.entity.HttpStatus;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;
import com.glushkov.io.ResourceReader;
import com.glushkov.util.RequestParser;
import com.glushkov.util.ResponseWriter;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;

public class RequestHandler {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(RequestHandler.class);

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
            byte[] content = ResourceReader.readContent(webAppPath, parsedRequest.getUri());
            responseWriter.writeResponse(HttpStatus.OK, content);
        } catch (ServerException serverException) {
            serverException.printStackTrace();
            responseWriter.writeResponse(serverException.getHttpStatus());
        } catch (Exception exception) {
            logger.error("Exception while handle(): ", exception);
            exception.printStackTrace();
            responseWriter.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.debug("Finish of request processing");
    }
}
