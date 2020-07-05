package com.glushkov.io;


import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceReader {

    public static String readContent(String webAppPath, String uri) {

        try {
            if (!uri.equals("/")) {
                if (new File(String.valueOf(Path.of(webAppPath, uri).toAbsolutePath())).exists()) {

                    Path pathToFile = Path.of(webAppPath, uri).toAbsolutePath();
                    return Files.readString(Paths.get(String.valueOf(pathToFile)));

                } else throw new ServerException(HttpStatus.NOT_FOUND);
            } else throw new ServerException(HttpStatus.BAD_REQUEST);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
