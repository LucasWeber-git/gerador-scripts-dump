package org.example.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileUtils {

    private static final String BASE_PATH = "src/main/resources/output/";

    public static void resetFile(String relativePath) {
        Path filepath = Paths.get(BASE_PATH + relativePath);

        File directory = filepath.toFile().getParentFile();
        directory.mkdirs();

        writeString(filepath, "", TRUNCATE_EXISTING);
    }

    public static void writeToFile(String relativePath, String content) {
        resetFile(relativePath);

        Path filepath = Paths.get(BASE_PATH + relativePath);

        writeString(filepath, content, TRUNCATE_EXISTING);
    }

    public static void appendToFile(String relativePath, String content) {
        Path filepath = Paths.get(BASE_PATH + relativePath);

        writeString(filepath, content, APPEND);
    }

    private static void writeString(Path filepath, String content, StandardOpenOption option) {
        try {
            Files.writeString(filepath, content, option);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
