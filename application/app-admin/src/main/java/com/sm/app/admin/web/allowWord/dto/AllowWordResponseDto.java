package com.sm.app.admin.web.allowWord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.app.domainrdb.core.allowWord.entity.AllowWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AllowWordResponseDto {
    private Long id;
    private String word;
    private boolean deleted;
    private String regId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    private String modId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    public static AllowWordResponseDto from(AllowWord allowWord) {
        return AllowWordResponseDto.builder()
                .id(allowWord.getId())
                .word(allowWord.getWord())
                .deleted(allowWord.isDeleted())
                .regId(allowWord.getRegId())
                .regDate(allowWord.getRegDate())
                .modId(allowWord.getModId())
                .modDate(allowWord.getModDate())
                .build();
    }
}
