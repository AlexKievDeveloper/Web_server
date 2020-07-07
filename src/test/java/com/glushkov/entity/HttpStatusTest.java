package com.glushkov.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpStatusTest {

    @Test
    public void getStatusLine() {
        String expected = "HTTP/1.1 200 OK";
        HttpStatus httpStatus = HttpStatus.OK;

        String actual = httpStatus.getStatusLine();

        assertEquals(expected, actual);
    }
}