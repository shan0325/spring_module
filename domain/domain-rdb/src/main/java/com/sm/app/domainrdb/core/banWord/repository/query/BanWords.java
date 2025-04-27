package com.sm.app.domainrdb.core.banWord.repository.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BanWords {
    private Long id;
    private String word;
    private String regId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    private String modId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    @QueryProjection
    public BanWords(Long id, String word, String regId, LocalDateTime regDate, String modId, LocalDateTime modDate) {
        this.id = id;
        this.word = word;
        this.regId = regId;
        this.regDate = regDate;
        this.modId = modId;
        this.modDate = modDate;
    }
}
