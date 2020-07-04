package com.glushkov;

public class ServerStarter {
    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setPathToResources("src/main/resources/webapp");
        server.start();
    }
}
