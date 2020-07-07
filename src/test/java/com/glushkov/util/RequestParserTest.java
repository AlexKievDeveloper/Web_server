package com.glushkov.util;

import com.glushkov.entity.HttpMethod;
import com.glushkov.entity.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestParserTest {

    String requestLine;
    RequestParser requestParser;

    @Before
    public void setUp(){
        requestLine = "GET /about.html HTTP/1.1";
        requestParser = new RequestParser();
    }

    @Test
    public void parseRequest() {
        File pathToRequest = new File("src/test/RequestGET.txt");
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToRequest)))){

            Request request = requestParser.parseRequest(fileReader);
            Map<String, String> map = request.getHeaders();

            String expected = "localhost:3000";
            String actual = request.getHeaders().get("Host:");
            assertEquals(expected, actual);

            HttpMethod expectedMethod = HttpMethod.GET;
            HttpMethod actualMethod = request.getHttpMethod();
            assertEquals(expectedMethod, actualMethod);

            String expectedUri = "/about.html";
            String actualUri = request.getUri();
            assertEquals(expectedUri, actualUri);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void injectUrlAndHttpMethod() {
        Request request = new Request();
        HttpMethod httpExpected = HttpMethod.GET;
        String uriExpected = "/about.html";
        requestParser.injectUrlAndHttpMethod(request, requestLine);

        HttpMethod httpActual = request.getHttpMethod();
        String uriActual = request.getUri();

        assertEquals(httpExpected, httpActual);
        assertEquals(uriExpected, uriActual);
    }

    @Test
    public void injectHeaders() {
        File pathToRequest = new File("src/test/RequestGET.txt");
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToRequest)))){

            Request request = requestParser.parseRequest(fileReader);

            String expected = "localhost:3000";
            String actual = request.getHeaders().get("Host:");

            assertEquals(expected, actual);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}