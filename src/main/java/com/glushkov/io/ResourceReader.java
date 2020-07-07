package com.glushkov.io;


import ch.qos.logback.classic.Logger;
import com.glushkov.entity.HttpStatus;
import com.glushkov.exception.ServerException;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceReader {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ResourceReader.class);

    public static String readContent(String webAppPath, String uri) {
        try {
            if (!uri.equals("/")) {
                if (new File(String.valueOf(Path.of(webAppPath, uri).toAbsolutePath())).exists()) {

                    Path pathToFile = Path.of(webAppPath, uri).toAbsolutePath();
                    return Files.readString(Paths.get(String.valueOf(pathToFile)));
                }
                logger.info("uri not found: {}", uri);
                throw new ServerException(uri + " not found", HttpStatus.NOT_FOUND);
            }
            logger.info("uri was: {}", uri);
            throw new ServerException("uri was:" + uri, HttpStatus.BAD_REQUEST);
        } catch (IOException ioException) {
            logger.error("IO exception while reading content:", ioException);
            ioException.printStackTrace();
            throw new ServerException("IO server exception while reading the contents of the file, path was: " +
                    ioException + Path.of(webAppPath, uri).toAbsolutePath().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}