
package com.glushkov.server;

import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String webAppPath;

    private int port;

    public void start() {
        logger.info("Start working");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.debug("New server socket opened!");

            while (true) {
                Socket socket = serverSocket.accept();
                logger.debug("New socket created");

                Runnable target = () -> {
                    try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         BufferedOutputStream socketWriter = new BufferedOutputStream(socket.getOutputStream())) {
                        RequestHandler requesthandler = new RequestHandler(socketReader, socketWriter, webAppPath);
                        requesthandler.handle();
                    } catch (IOException ioException) {
                        logger.error("Error while handle", ioException);
                        throw new ServerException("Error while handle", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                };
                new Thread(target).start();
            }
        } catch (
                IOException ioException) {
            logger.error("Error while Server.start():", ioException);
            throw new ServerException("Error while Server.start()", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public int getPort() {
        return port;
    }

    public void setWebAppPath(String webAppPaths) {
        this.webAppPath = webAppPaths;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
