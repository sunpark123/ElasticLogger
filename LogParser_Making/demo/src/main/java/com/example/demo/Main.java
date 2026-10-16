package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.ParserFormatEntity;
import com.example.demo.FormatEntity;
import org.apache.commons.logging.Log;

import javax.swing.text.html.parser.Parser;

public class Main {
    //Login - (Hint, Pattern)
    private final HashMap<String, ParserFormatEntity> PatternList = new HashMap<>();


    private void parseFormatLoad() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream =
                     Main.class.getResourceAsStream("/ParseFormat.json")) {

            if (inputStream == null) {
                throw new IOException("ParseFormat.json 파일을 찾을 수 없습니다.");
            }

            Map<String, ParserFormatEntity> formats =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<HashMap<String, ParserFormatEntity>>() {}
                    );

            PatternList.putAll(formats);
        }

        System.out.println("등록된 패턴: " + PatternList.keySet());
    }
    private String LogParerJson(String rawLog) {
        String matchedPatternLogName = null;

        for (Map.Entry<String, ParserFormatEntity> entry : PatternList.entrySet()) {
            String logName = entry.getKey();
            ParserFormatEntity patternEntity = entry.getValue();

            if (rawLog.contains(patternEntity.getHint())) {
                if (patternEntity.getPattern().matcher(rawLog).matches()) {
                    matchedPatternLogName = logName;
                    break;
                }
            }
        }
        System.out.println("-----------------------------------------");
        System.out.println("찾은 패턴 : " + matchedPatternLogName);
        try {
            if(matchedPatternLogName == null){
                System.out.println("Format Can't Found");

                throw new RuntimeException("Format Can't Found");
            }

            System.out.println("Format Match Started;");

            ParserFormatEntity parserEntity = PatternList.get(matchedPatternLogName);

            Pattern parsingPattern = parserEntity.getPattern();
            Matcher matcher = parsingPattern.matcher(rawLog);

            if(matcher.matches()) {

                System.out.println("Format Matched");
                System.out.println("GroupCount : " + matcher.groupCount());

                for(Integer groupId = 1; groupId <= matcher.groupCount(); groupId++){
                    List<String> groupNameList = parserEntity.getGroupList();

                    System.out.println(groupNameList.get(groupId-1) + " : " + matcher.group(groupId));
                }
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------");

        return "asd";
    }
    public static void main(String[] args) {
        Main main = new Main();
        try{
            main.NanoTimePrinter("ParseFormatLoad", true);
            main.parseFormatLoad();
            main.NanoTimePrinter("ParseFormatLoad", false);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }

        String rawLog = "2026-10-16 15:35:21.482 [INFO] /api/v1/users/profile";
        main.NanoTimePrinter("Try Login Parse", true);
        main.LogParerJson(rawLog);
        main.NanoTimePrinter("Try Login Parse", false);

        rawLog = "2026-10-32 11:20:21.482 [WARING] /api/login/error";
        main.NanoTimePrinter("Try Login Parse_2", true);
        main.LogParerJson(rawLog);
        main.NanoTimePrinter("Try Login Parse_2", false);

        rawLog = "26/02/03-09:58:12 device-1 192.168.0.1 Used Wrong Location With URL";
        main.NanoTimePrinter("Try System Parse", true);
        main.LogParerJson(rawLog);
        main.NanoTimePrinter("Try System Parse", false);

        main.NanoTimePrinter("PrintALL", true);
    }



    public HashMap<String, Long> NanoTimer = new LinkedHashMap<>();
    public void NanoTimePrinter(String LogName, boolean status){
        if(Objects.equals(LogName, "PrintALL")){
            NanoTimer.forEach((logName, time) -> {
                System.out.println("-------------------------------------");
                System.out.println("LogName : " + logName);
                System.out.println("Time : " + time+" ms");
                System.out.println("-------------------------------------");
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
