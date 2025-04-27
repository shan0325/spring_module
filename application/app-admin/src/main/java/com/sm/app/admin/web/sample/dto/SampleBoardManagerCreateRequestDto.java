package com.sm.app.admin.web.sample.dto;

import com.sm.app.domainrdb.core.sample.enums.BoardTypeEnum;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
public class SampleBoardManagerCreateRequestDto {
    @NotBlank(message = "게시판 이름은 필수 입력값입니다.")
    private String name;

    private String description;

    @NotNull(message = "게시판 타입은 필수 입력값입니다.")
    private BoardTypeEnum boardType;

    @NotNull(message = "사용 여부는 필수 입력값입니다.")
    private Character useYn;
}
