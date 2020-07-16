package com.glushkov.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpStatusTest {

    @Test
    public void getStatusLineTest() {
        //prepare
        String expected = "HTTP/1.1 200 OK";
        HttpStatus httpStatus = HttpStatus.OK;
        //when
        String actual = httpStatus.getStatusLine();
        //then
        assertEquals(expected, actual);
    }
}