package com.question.springtest.api.dto;

import lombok.Getter;

@Getter
public class TestGetterDto {

    public String publicVar;
    protected String protectedVar;
    String defaultVar;
    private String privateVar;

}
