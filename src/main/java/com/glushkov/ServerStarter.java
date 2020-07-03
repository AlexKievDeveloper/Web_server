package com.glushkov;

import com.glushkov.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerStarter {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setPort(3000);
        server.setPathToResources("src/main/resources/webapp");
        server.start();
    }
}
