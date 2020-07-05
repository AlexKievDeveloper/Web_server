package com.glushkov.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private String webAppPath;
    private int port;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

                    RequestHandler requesthandler = new RequestHandler(socketReader, socketWriter, webAppPath);
                    requesthandler.handle();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setWebAppPath(String webAppPaths) {
        this.webAppPath = webAppPaths;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

