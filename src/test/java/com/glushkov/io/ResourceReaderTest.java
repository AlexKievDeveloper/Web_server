package com.glushkov.io;


import com.glushkov.exception.ServerException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceReaderTest {

    @Test
    public void readContentTest() throws IOException {
        //prepare
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

        //when
        InputStream inputStream = ResourceReader.readContent("src/test/", "RequestGET.txt");
        byte[] actual = inputStream.readAllBytes();

        //then
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void readContentTestForServerExceptionThrown() {
            Exception exception = assertThrows(ServerException.class, () -> {

                ResourceReader.readContent("src/test/", "RequestGE.txt");
            });

            String expectedMessage = "File not found";
            String actualMessage = exception.getMessage();

           assertTrue(actualMessage.contains(expectedMessage));
    }
}
