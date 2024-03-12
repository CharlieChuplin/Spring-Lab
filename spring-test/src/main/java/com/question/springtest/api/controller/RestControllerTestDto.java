package com.question.springtest.api.controller;

import com.question.springtest.api.dto.BoardDto;
import com.question.springtest.api.dto.TestDto;
import com.question.springtest.api.dto.TestGetterDto;
import com.question.springtest.api.dto.TestVariousDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class RestControllerTestDto {

    @PostMapping("/testDto")
    public TestDto dtoTest(@RequestBody TestDto dto) {
        return dto;
    }

    @PostMapping("/testGetterDto")
    public TestGetterDto dtoGetterTest(@RequestBody TestGetterDto dto) {
        return dto;
    }

    @PostMapping("/testVariousDto")
    public TestVariousDto testVariousDto(@RequestBody TestVariousDto dto) {

        /**
         * List<Object>
         * "list": ["one","two","three"],
         */
        log.info("List<Object>");
        String getList = dto.getList().toString();
        System.out.println("getList = " + getList);

        /**
         * List<Map<String, String>>
         * "mapList": [
         *         {
         *             "a": "one",
         *             "b": "two",
         *             "c": "three"
         *         }
         *     ],
         */
        log.info("List<Map<String, String>>");
        String getMapList = dto.getMapList().toString();
        System.out.println("getMapList = " + getMapList);

        /**
         * Map <String, Long>
         * "map": {
         *         "one": 1,
         *         "two": 2
         *     },
         */
        log.info("Map<String, Long>");
        String getMap = dto.getMap().toString();
        System.out.println("getMap = " + getMap);

        /**
         * Map<String, List<Object>>
         * "listMap": {
         *         "fruits": ["apple", "banana","grape"],
         *         "animals" : ["monkey", "lion", "tiger"]
         *     },
         */
        log.info("Map<String, List<Object>>");
        String getListMap = dto.getListMap().toString();
        System.out.println("getListMap = " + getListMap);

        /**
         * List<AnimalsDto>
         * "animals":["lion","monkey","tiger"],
         */
        log.info("List<AnimalsDto>");
        dto.getAnimals().forEach(animal -> {
            System.out.println("\tSpecies: " + animal.getSpecies());
        });

        // Map<String, BookDto>
        /**
         * "books": {
         *         "1" : {
         *             "author" : "토비",
         *             "title" : "스프링"
         *         },
         *         "2" : {
         *             "author" : "김영한",
         *             "title" : "JPA"
         *         }
         *     }
         */
        log.info("Map<String, BookDto>");
        dto.getBooks().forEach((k, v) -> {
            System.out.println("key : " + k);
            System.out.println("\tAuthor: " + v.getAuthor());
            System.out.println("\tTitle: " + v.getTitle());
        });

        return dto;
    }

    @PostMapping("/testImg")
    public ResponseEntity<byte[]> testImgOne(@ModelAttribute BoardDto dto) {
        System.out.println("Title: " + dto.getTitle());
        System.out.println("Content: " + dto.getContent());
        dto.getImages().forEach(img -> System.out.println(img.getOriginalFilename()));


        // 이미지 응답 방법
        try {
            MultipartFile file = dto.getImages().get(0);

            // 이미지를 응답하기 위해서는 byte[]로 응답
            // 파일을 byte[]로 변환
            byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());

            //헤더의 정보를 입력시 한글은 인코딩 해야한다.
            String thumbImg = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

            // 헤더의 Content-Type, Content-Disposition 등 파일의 정보 추가 및 설정
            return ResponseEntity.ok()
                    .header("Content-Type", file.getContentType())
                    .header("Content-Disposition", "inline; filename=" + thumbImg)
                    .body(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}