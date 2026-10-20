package com.example.demo.Entity;


import java.util.List;
import java.util.regex.Pattern;

public class ParserFormatEntity {
    private String hint;
    private Pattern pattern;
    private List<String> groupList;
    public ParserFormatEntity() {
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }
}
