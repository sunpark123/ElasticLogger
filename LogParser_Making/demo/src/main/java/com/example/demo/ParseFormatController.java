package com.example.demo;

import com.example.demo.Entity.ParserFormatEntity;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.LogController.PatternList;
import static com.example.demo.LogController.objectMapper;

public class ParseFormatController {
    public void LoadParseFormats(){
        try (InputStream inputStream = LogController.class.getResourceAsStream("/ParseFormat.json")) {
            if (inputStream == null) {
                throw new IOException("ParseFormat.json 파일을 찾을 수 없습니다.");
            }

            Map<String, ParserFormatEntity> formats =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<HashMap<String, ParserFormatEntity>>() {}
                    );

            PatternList.putAll(formats);
        } catch (IOException e){
            System.out.println("IO Exception");
        }
    }
}
