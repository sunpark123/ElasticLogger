package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoggingController {

    private static final HashMap<String, String> logs = new LinkedHashMap<>();


    public void addLog(String key, String value) {
        try {
            Files.writeString(
                    Path.of("src/main/resources/Output/Logging.txt"),
                    (key + " : " + value) + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
