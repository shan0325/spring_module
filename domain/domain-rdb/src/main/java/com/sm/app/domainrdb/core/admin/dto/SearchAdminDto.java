package com.sm.app.domainrdb.core.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchAdminDto {
    private String searchType;
    private String search;

    public static SearchAdminDto of(String searchType, String search) {
        return SearchAdminDto.builder()
                .searchType(searchType)
                .search(search)
                .build();
    }
}
