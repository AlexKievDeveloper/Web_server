package com.glushkov.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpMethodTest {

    @Test
    public void getByName() {
        HttpMethod expected = HttpMethod.GET;

        HttpMethod actual = HttpMethod.getByName("get");

        assertEquals(expected, actual);
    }
}