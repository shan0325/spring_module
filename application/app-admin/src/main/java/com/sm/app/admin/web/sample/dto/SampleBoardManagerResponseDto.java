package com.sm.app.admin.web.sample.dto;

import com.sm.app.domainrdb.core.sample.enums.BoardTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleBoardManagerResponseDto {
    private Long id;
    private String name;
    private String description;
    private BoardTypeEnum boardType;
    private Character useYn;
}
