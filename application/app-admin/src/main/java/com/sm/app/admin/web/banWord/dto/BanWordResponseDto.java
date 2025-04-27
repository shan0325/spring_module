package com.sm.app.admin.web.banWord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.app.domainrdb.core.banWord.entity.BanWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BanWordResponseDto {
    private Long id;
    private String word;
    private boolean deleted;
    private String regId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    private String modId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    public static BanWordResponseDto from(BanWord banWord) {
        return BanWordResponseDto.builder()
                .id(banWord.getId())
                .word(banWord.getWord())
                .deleted(banWord.isDeleted())
                .regId(banWord.getRegId())
                .regDate(banWord.getRegDate())
                .modId(banWord.getModId())
                .modDate(banWord.getModDate())
                .build();
    }
}
