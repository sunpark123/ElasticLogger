package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.Entity.ParserFormatEntity;


public class LogController {
    public static int LogID = 0;
    public static ObjectMapper objectMapper = new ObjectMapper();
    //Login - (Hint, Pattern)
    public static final HashMap<String, ParserFormatEntity> PatternList = new HashMap<>();

    public static void main(String[] args) {
        ParseFormatController parseFormatController = new ParseFormatController();
        parseFormatController.LoadParseFormats();
    }

    public static void logParse(String rawLog) {
        LogParser logParser = new LogParser();

        HashMap<String, String> parsingLogs = logParser.RawLogParser(rawLog);
        String jsonString = logParser.LogListToString(parsingLogs);
    }



    //Don't use in ElasticLogger Project ( This is Not Non-blocking IO )
    private void SaveJsonStringToFile(String jsonString) throws IOException {
        Files.writeString(
                Path.of("src/main/resources/Output/parsing.json"),
                jsonString + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public HashMap<String, Long> NanoTimer = new LinkedHashMap<>();
    public void NanoTimePrinter(String LogName, boolean status){
        if(Objects.equals(LogName, "PrintALL")){
            System.out.println("****************************Parser LOG***************************");
            NanoTimer.forEach((logName, time) -> {
                String returnString = "";
                returnString += "LogName : " + logName + " / Time : " + time + " ms";
                try {
                    Files.writeString(
                            Path.of("parsingLog.txt"),
                            returnString + System.lineSeparator(),
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return;
        }

        //status = true -> start
        if(status){
            NanoTimer.put(LogName, System.nanoTime());
        }else{
            Long nanoTime = System.nanoTime();
            Long time = (long) ((nanoTime - NanoTimer.get(LogName)) / 1_000_000.0);
            NanoTimer.put(LogName, time);

        }
    }
}
