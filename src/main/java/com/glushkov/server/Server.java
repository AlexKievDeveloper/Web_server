package com.glushkov.server;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Server.class);

    private String webAppPath;

    private int port;

    public void start() {
        logger.info("Start working");
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedOutputStream socketWriter = new BufferedOutputStream(socket.getOutputStream())) {

                    RequestHandler requesthandler = new RequestHandler(socketReader, socketWriter, webAppPath);
                    requesthandler.handle();

                } catch (IOException ioException) {
                    logger.error("IOException while Server.start(): ", ioException);
                    ioException.printStackTrace();
                }
            }
        } catch (IOException ioException) {
            logger.error("IOException while ServerSocket(port) created: ", ioException);
            ioException.printStackTrace();
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

