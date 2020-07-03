package com.glushkov;

public class ClientStarter {
    public static void main(String[] args) {
        Client client = new Client();
        client.setPort(3000);
        client.setHost("127.0.0.1");
        client.sendRequest();
    }
}
