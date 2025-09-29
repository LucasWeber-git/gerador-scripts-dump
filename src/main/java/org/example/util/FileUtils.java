package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private static final String PATH_OUTPUT = "src/main/resources/output/";

    public static void writeToFile(String filename, String content) {
        Path path = Paths.get(PATH_OUTPUT + filename);

        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
