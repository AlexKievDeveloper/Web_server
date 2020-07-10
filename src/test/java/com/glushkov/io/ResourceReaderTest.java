package com.glushkov.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceReaderTest {

    @Test
    public void readContent() {
        String example = "GET /about.html HTTP/1.1\n" +
                "Host: localhost:3000\n" +
                "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                "Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "\n";

        byte[] expected = example.getBytes();

        byte[] actual = ResourceReader.readContent("src/test/", "RequestGET.txt");

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
