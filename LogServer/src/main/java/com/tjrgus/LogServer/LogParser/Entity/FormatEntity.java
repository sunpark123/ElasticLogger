package com.example.demo.Entity;

public class FormatEntity {
    private String LogName;
    private ParserFormatEntity parserFormatEntity;

    public FormatEntity(String logName, ParserFormatEntity parserFormatEntity) {
        LogName = logName;
        this.parserFormatEntity = parserFormatEntity;
    }

    public FormatEntity() {
    }

    public String getLogName() {
        return LogName;
    }

    public void setLogName(String logName) {
        LogName = logName;
    }

    public ParserFormatEntity getParserFormatEntity() {
        return parserFormatEntity;
    }

    public void setParserFormatEntity(ParserFormatEntity parserFormatEntity) {
        this.parserFormatEntity = parserFormatEntity;
    }
}
