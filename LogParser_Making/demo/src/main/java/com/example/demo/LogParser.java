package com.example.demo;

import com.example.demo.Entity.ParserFormatEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.LogController.*;

public class LogParser {
    private String findLogFormatName(String rawLog){
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
        return matchedPatternLogName;
    }
    public HashMap<String,String> RawLogParser(String rawLog) {

        String formatName = findLogFormatName(rawLog);
        HashMap<String, String> parsingLogs = new LinkedHashMap<>();
        parsingLogs.put("LogID", String.valueOf(LogController.LogID));

        if(formatName == null){
            parsingLogs.put("level","ERROR");
            parsingLogs.put("rawLog",rawLog);
            return parsingLogs;
        }



        try {
            ParserFormatEntity parserEntity = PatternList.get(formatName);

            Pattern parsingPattern = parserEntity.getPattern();
            Matcher matcher = parsingPattern.matcher(rawLog);

            if(matcher.matches()) {
                for(int groupId = 1; groupId <= matcher.groupCount(); groupId++){
                    List<String> groupNameList = parserEntity.getGroupList();

                    String groupName = groupNameList.get(groupId-1);
                    String groupValue = matcher.group(groupId);
                    System.out.println(groupName + " : " + groupValue);
                    parsingLogs.put(groupName, groupValue);
                }
                return parsingLogs;
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }

        parsingLogs.put("level","ERROR");
        parsingLogs.put("rawLog",rawLog);
        return parsingLogs;
    }
    public String LogListToString(HashMap<String,String> parsingLogList){
        try{
            return objectMapper.writeValueAsString(parsingLogList);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return "";
    }
}
