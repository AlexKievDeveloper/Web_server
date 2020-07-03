package com.glushkov;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;


public class ServerTest {
    String LINE_END = "\n";
    String expectedIndexHtmlFile = new String(Files.readAllBytes(Paths.get("src/main/resources/webapp/index.html")));
    String index = "GET /index.html HTTP/1.1";
    String style = "GET /css/style.css HTTP/1.1";
    String host = "127.0.0.1";
    int port = 3000;

    public ServerTest() throws IOException {
    }

    @Before
    public void setUp(){
        Server server = new Server();
        server.setPort(3000);
        server.setPathToResources("src/main/resources/webapp");
        server.start();
    }

   @Test
    public void testRequest() throws IOException {
       Socket socket = new Socket(host, port);
       BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

       socketWriter.write(index);
       socketWriter.write(LINE_END);
       socketWriter.flush();

       StringBuilder content = new StringBuilder();
       for (int i = 0; i < 20; i++) {
           String line = socketReader.readLine();
           content.append(line).append("\n");
           if (line.equals("</html>"))break;
       }
       System.out.println(content);

       assertEquals(expectedIndexHtmlFile, content.toString());

       socket.close();
       socketReader.close();
       socketWriter.close();
    }
}
