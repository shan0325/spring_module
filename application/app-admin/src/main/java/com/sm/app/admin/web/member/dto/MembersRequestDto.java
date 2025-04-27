package com.sm.app.admin.web.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersRequestDto {
    private String searchType;
    private String search;
}
