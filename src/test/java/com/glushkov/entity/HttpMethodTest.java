package com.glushkov.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpMethodTest {

    @Test
    public void getByNameOrNullTest() {
        //prepare
        HttpMethod expected = HttpMethod.GET;
        //when
        HttpMethod actual = HttpMethod.getByNameOrNull("get");
        //then
        assertEquals(expected, actual);
    }
}