package com.sm.app.admin.web.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleBoardCreateRequestDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    private String content;

    @NotNull(message = "게시판 관리자 ID는 필수 입력값입니다.")
    private Long boardManagerId;
}
