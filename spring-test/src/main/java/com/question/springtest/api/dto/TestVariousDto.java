package com.question.springtest.api.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
public class TestVariousDto{

    public String publicVar;
    protected String protectedVar;
    String defaultVar;
    private String privateVar;

    List<Object> list;
    List<Map<String, String>> mapList;
    Map<String,Long> map;
    Map<String, List<Object>> listMap;

    List<AnimalsDto> animals;
    Map<Long, BookDto> books;
}
