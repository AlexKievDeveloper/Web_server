
package com.glushkov.util;

import com.glushkov.entity.HttpStatus;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class ResponseWriterITest {

    @Test
    public void writeResponseTest() throws IOException {
        //prepare
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("src/test/Output.txt"))) {
            ResponseWriter responseWriter = new ResponseWriter(outputStream);
            //when
            responseWriter.writeResponse(HttpStatus.OK);
        }

        String expected = HttpStatus.OK.getStatusLine() + "\n\n";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/Output.txt"))) {

            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();

            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            String actual = stringBuilder.toString();

            //then
            assertEquals(expected, actual);
        }
    }
}
