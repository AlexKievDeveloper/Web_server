package com.glushkov.util;

import com.glushkov.entity.HttpMethod;
import com.glushkov.entity.Request;
import com.glushkov.exception.ServerException;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;


public class RequestParserITest {
    private String requestGet = "GET /about.html HTTP/1.1\n" +
            "Host: localhost:3000\n" +
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:78.0) Gecko/20100101 Firefox/78.0\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3\n" +
            "Accept-Encoding: gzip, deflate\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "\n";

    String emptyStr = "";

    private RequestParser requestParser = new RequestParser();


    @Test
    public void parseRequestTest() throws Exception {
        //prepare
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestGet.getBytes())))) {
            Request request = requestParser.parseRequest(fileReader);
            String expected = "localhost:3000";
            HttpMethod expectedMethod = HttpMethod.GET;
            String expectedUri = "/about.html";

            //when
            String actual = request.getHeaders().get("Host:");
            HttpMethod actualMethod = request.getHttpMethod();
            String actualUri = request.getUri();

            //then
            assertEquals(expected, actual);
            assertEquals(expectedMethod, actualMethod);
            assertEquals(expectedUri, actualUri);
        }
    }

    @Test
    public void parseRequestTestServerExceptionThrown() throws Exception {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(emptyStr.getBytes())))) {
            String expectedMessage = "Request line was null or empty";
            //when
            Exception exception = assertThrows(ServerException.class, () -> {
                requestParser.parseRequest(fileReader);
            });
            String actualMessage = exception.getMessage();

            //then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    public void injectUrlAndHttpMethodTest() {
        //prepare
        String requestLine = "GET /about.html HTTP/1.1";
        Request request = new Request();
        HttpMethod httpExpected = HttpMethod.GET;
        String uriExpected = "/about.html";

        //when
        requestParser.injectUrlAndHttpMethod(request, requestLine);

        //then
        HttpMethod httpActual = request.getHttpMethod();
        String uriActual = request.getUri();

        assertEquals(httpExpected, httpActual);
        assertEquals(uriExpected, uriActual);
    }

    @Test
    public void injectUrlAndHttpMethodTestForServerExceptionThrown() {
        //prepare
        String requestLine = "PURGE /about.html HTTP/1.1";
        Request request = new Request();
        String expectedMessage = "Error in injectUrlAndHttpMethod, method was not valid: PURGE";

        //when
        Exception exception = assertThrows(ServerException.class, () -> {
            requestParser.injectUrlAndHttpMethod(request, requestLine);
        });
        String actualMessage = exception.getMessage();

        //then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void injectHeadersTest() throws Exception {
        //prepare
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestGet.getBytes())))) {
            String expected = "localhost:3000";

            //when
            Request request = requestParser.parseRequest(fileReader);
            String actual = request.getHeaders().get("Host:");

            //then
            assertEquals(expected, actual);
        }
    }
}