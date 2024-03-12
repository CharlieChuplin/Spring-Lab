package com.question.springtest.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RestControllerTestBasic {

    @GetMapping("/test")
    public String test() {
        return "done";
    }

    // @PathVariable
    // 경로변수와 매개변수가 다를때 (value = "경로변수") 로 매핑
    @GetMapping("/path/diff/{PathVariableDiff}")
    public String pathVariableDiff(@PathVariable(value = "PathVariableDiff") String something) {
        return something;
    }

    // @PathVariable
    // 경로변수와 매개변수가 같을때 매핑시 (value = "") 생략가능
    @GetMapping("/path/same/{PathVariableSame}")
    public String pathVariableSame(@PathVariable String PathVariableSame) {
        return PathVariableSame;
    }

}
