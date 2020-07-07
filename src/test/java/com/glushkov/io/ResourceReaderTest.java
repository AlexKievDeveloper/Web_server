package com.glushkov.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceReaderTest {

    @Test
    public void readContent() {
        String expected = "body {\n" +
                "    background-color: aqua;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    font-size: 20px;\n" +
                "}";

        String actual = ResourceReader.readContent("src/main/resources/webapp/", "css/style.css");

        assertEquals(expected,actual);
    }
}