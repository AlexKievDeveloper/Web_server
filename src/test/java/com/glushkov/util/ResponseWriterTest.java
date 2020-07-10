
package com.glushkov.util;

import com.glushkov.entity.HttpStatus;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ResponseWriterTest {

    @Test
    public void writeResponse() throws IOException {
        File pathToOutput = new File("src/test/Output.txt");

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pathToOutput))) {

            ResponseWriter responseWriter = new ResponseWriter(outputStream);
            responseWriter.writeResponse(HttpStatus.OK);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        HttpStatus httpStatus = HttpStatus.OK;
        String expected = httpStatus.getStatusLine() + "\n\n";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToOutput))) {

            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();

            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            String actual = stringBuilder.toString();
            assertEquals(expected, actual);
        }
    }
}
