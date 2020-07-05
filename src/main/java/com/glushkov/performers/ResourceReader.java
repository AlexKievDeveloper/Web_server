package com.glushkov.performers;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceReader {

    private String webAppPath;

    public ResourceReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public String getResource(String uri) {
        try {
            if (!uri.equals("/")) {

                Path pathToFile = Path.of(webAppPath, uri).toAbsolutePath();
                File file = new File(String.valueOf(pathToFile));

                if (file.exists()) {
                    return Files.readString(Paths.get(String.valueOf(pathToFile)));
                } else throw new RuntimeException("File not found, path:" + pathToFile.toString());
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        throw new RuntimeException("File not found, path:" + webAppPath + uri);
    }
}
