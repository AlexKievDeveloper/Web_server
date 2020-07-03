package com.glushkov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    private static final String LINE_END = "\n";
    private String pathToResources;
    private int port;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

                    while (true) {
                        String lineFromClient = socketReader.readLine();
                        if (lineFromClient.isEmpty()) {
                            break;
                        } else {
                            if (lineFromClient.contains("GET")) {
                                String request = lineFromClient.substring(lineFromClient.indexOf('T') + 2, lineFromClient.indexOf('H') - 1);

                                if (new File(pathToResources + request).exists() && request.length() > 1) {
                                    String result = getResourcesFromPath(pathToResources + request);
                                    socketWriter.write("HTTP/1.1 200 OK");
                                    socketWriter.write(LINE_END);
                                    socketWriter.write(LINE_END);
                                    socketWriter.write(result);
                                    socketWriter.write(LINE_END);
                                    socketWriter.flush();
                                } else {
                                    socketWriter.write("HTTP/1.1 404 Not Found");
                                    socketWriter.write(LINE_END);
                                    socketWriter.write(LINE_END);
                                    socketWriter.flush();
                                }
                            } else {
                                socketWriter.write("HTTP/1.1 404 Not Found");
                                socketWriter.write(LINE_END);
                                socketWriter.write(LINE_END);
                                socketWriter.flush();
                            }
                        }
                    }
                } catch (IOException | NullPointerException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String getResourcesFromPath(String request) {
        try {
            return new String(Files.readAllBytes(Paths.get(request)));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public String getPathToResources() {
        return pathToResources;
    }

    public void setPathToResources(String pathToResources) {
        this.pathToResources = pathToResources;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

