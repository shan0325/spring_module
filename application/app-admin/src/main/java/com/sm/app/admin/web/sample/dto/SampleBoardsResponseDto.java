package com.sm.app.admin.web.sample.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SampleBoardsResponseDto {
    private Long id;
    private String title;
    private String content;
    private Character useYn;
    private String createdBy;
    private LocalDateTime createdDate;
    private Long sampleBoardManagerId;
}
