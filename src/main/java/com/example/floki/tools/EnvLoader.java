package com.example.floki.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EnvLoader {
    public static void loadEnv() {
        try (Stream<String> stream = Files.lines(Paths.get(".env"))) {
            stream.filter(line -> !line.startsWith("#") && line.contains("="))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            System.setProperty(parts[0], parts[1]);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }
}