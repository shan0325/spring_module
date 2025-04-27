package com.sm.app.admin.web.resource.dto;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ResourceCreateRequestDto {
    @NotNull(message = "사이트 타입은 필수 입력 값입니다.")
    private SiteTypeEnum siteType;

    @NotNull(message = "리소스명은 필수 입력 값입니다.")
    private String resourceName;

    @NotNull(message = "리소스 패턴은 필수 입력 값입니다.")
    private String resourcePattern;

    @NotNull(message = "HTTP 메소드는 필수 입력 값입니다.")
    private ResourceHttpMethodEnum httpMethod;

    @NotNull(message = "순서는 필수 입력 값입니다.")
    private Integer orderNum;

    public Resource toResource() {
        return Resource.builder()
                .siteType(siteType)
                .resourceName(resourceName)
                .resourcePattern(resourcePattern)
                .httpMethod(httpMethod)
                .orderNum(orderNum)
                .resourceType(ResourceTypeEnum.URL)
                .build();
    }
}
