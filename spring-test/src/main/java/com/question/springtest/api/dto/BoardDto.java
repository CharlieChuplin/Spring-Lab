package com.question.springtest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardDto {

    private String title;
    private String content;

    private List<MultipartFile> images;
}
