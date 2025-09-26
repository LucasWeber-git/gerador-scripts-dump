package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private static final Path PATH_CIDADES = Paths.get("src/main/resources/cidades.csv");
    private static final Path PATH_NOMES = Paths.get("src/main/resources/nomes.csv");
    private static final Path PATH_OUTPUT = Paths.get("src/main/resources/output.txt");

    private static final String COLON = ",";
    private static final String SEMI_COLON = ";";

    public static List<String> getCidades() {
        try (Stream<String> lines = Files.lines(PATH_CIDADES)) {
            return lines
                    .map(FileUtils::findCidadeInLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getNomes() {
        try (Stream<String> lines = Files.lines(PATH_NOMES)) {
            return lines
                    .map(line -> line.substring(0, line.indexOf(COLON)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(String content) {
        try {
            Files.writeString(PATH_OUTPUT, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findCidadeInLine(String line) {
        int occurrence = 3;
        int fromIndex = 0;
        int index = -1;

        for (int i = 0; i < occurrence; i++) {
            index = line.indexOf(SEMI_COLON, fromIndex);
            if (index == -1) break;
            fromIndex = index + SEMI_COLON.length();
        }

        return line.substring(index + 1, line.lastIndexOf(SEMI_COLON));
    }

}
