package com.sm.app.admin.web.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ResourcesResponseDto {
    private Long id;
    private SiteTypeEnum siteType;
    private String resourceName;
    private String resourcePattern;
    private ResourceHttpMethodEnum httpMethod;
    private Integer orderNum;
    private ResourceTypeEnum resourceType;
    private String regId;
    private String modId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    public static ResourcesResponseDto from(Resource resource) {
        return ResourcesResponseDto.builder()
                .id(resource.getId())
                .siteType(resource.getSiteType())
                .resourceName(resource.getResourceName())
                .resourcePattern(resource.getResourcePattern())
                .httpMethod(resource.getHttpMethod())
                .orderNum(resource.getOrderNum())
                .resourceType(resource.getResourceType())
                .regId(resource.getRegId())
                .modId(resource.getModId())
                .regDate(resource.getRegDate())
                .modDate(resource.getModDate())
                .build();
    }
}
