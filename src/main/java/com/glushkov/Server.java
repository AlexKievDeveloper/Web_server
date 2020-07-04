package com.glushkov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    private static final String LINE_END = "\r\n";
    private String pathToResource;
    private int port;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

                    String lineFromClient = socketReader.readLine();
                    if (lineFromClient.isEmpty()) {
                        break;
                    } else {
                        if (lineFromClient.contains("GET")) {
                            String request = lineFromClient.substring(lineFromClient.indexOf('T') + 2, lineFromClient.indexOf('H') - 1);
                            if (!request.equals("/") && new File(pathToResource, request).exists()) {
                                String result = getResourcesFromPath(pathToResource + request);
                                socketWriter.write("HTTP/1.1 200 OK");
                                socketWriter.write(LINE_END);
                                socketWriter.write(LINE_END);
                                socketWriter.write(result);
                                socketWriter.flush();
                            } else {
                                unknownFile(socketWriter);
                            }
                        } else {
                            unknownFile(socketWriter);
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void unknownFile(BufferedWriter socketWriter) throws IOException {
        socketWriter.write("HTTP/1.1 404 Not Found");
        socketWriter.write(LINE_END);
        socketWriter.write(LINE_END);
        socketWriter.flush();
    }

    private String getResourcesFromPath(String request) {
        try {
            return new String(Files.readAllBytes(Paths.get(request)));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public String getPathToResources() {
        return pathToResource;
    }

    public void setPathToResources(String pathToResources) {
        this.pathToResource = pathToResources;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

