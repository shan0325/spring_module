package com.sm.app.domainrdb.core.sample.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardTypeEnum {
    NOTICE("공지사항"),
    FREE("자유게시판"),
    GALLERY("갤러리"),
    QNA("질문과답변");

    private final String description;
}
