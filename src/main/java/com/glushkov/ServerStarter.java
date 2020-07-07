package com.glushkov;

import com.glushkov.server.Server;

public class ServerStarter {

    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setWebAppPath("src/main/resources/webapp");
        server.start();
    }
}
