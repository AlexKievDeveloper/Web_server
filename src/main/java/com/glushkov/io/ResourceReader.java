package com.glushkov.io;


import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public class ResourceReader {
    private static final Logger logger = LoggerFactory.getLogger(ResourceReader.class);

    public static InputStream readContent(String webAppPath, String uri) {

        Path path = Path.of(webAppPath, uri);
        File file = path.toFile();

        try {
            if (file.exists()) {
                return new FileInputStream(file);
            }
            logger.info("uri not found first case: " + uri);
            throw new ServerException("File not found", HttpStatus.NOT_FOUND);
        } catch (FileNotFoundException fileNotFoundException) {
            logger.info("uri not found: " + uri);
            throw new ServerException("File not found", HttpStatus.NOT_FOUND);
        }
    }
}
