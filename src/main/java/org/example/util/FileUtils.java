package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private static final String BASE_PATH = "src/main/resources/output/";

    public static void writeToFile(String relativePath, String content) {
        Path path = Paths.get(BASE_PATH + relativePath);

        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
