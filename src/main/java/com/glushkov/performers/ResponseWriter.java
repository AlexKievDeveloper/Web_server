package com.glushkov.performers;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {

    private static final String LINE_END = "\r\n";

    public static void writeSuccessResponse(BufferedWriter socketWriter, String content) {

        try {
            socketWriter.write("HTTP/1.1 200 OK");
            socketWriter.write(LINE_END);
            socketWriter.write(LINE_END);
            socketWriter.write(content);
            socketWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void writeBadRequestResponse(BufferedWriter socketWriter) {

        try {
            socketWriter.write("HTTP/1.1 400 Bad Request");
            socketWriter.write(LINE_END);
            socketWriter.write(LINE_END);
            socketWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void writeNotfoundResponse(BufferedWriter socketWriter) {

        try {
            socketWriter.write("HTTP/1.1 404 Not Found");
            socketWriter.write(LINE_END);
            socketWriter.write(LINE_END);
            socketWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void writeInternalServerErrorResponse(BufferedWriter socketWriter) {

        try {
            socketWriter.write("HTTP/1.1 500 Internal Server Error");
            socketWriter.write(LINE_END);
            socketWriter.write(LINE_END);
            socketWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
