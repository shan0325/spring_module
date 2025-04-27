package com.sm.app.domainrdb.core.resource.repository.query;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResourceExistsDto {
    private Long id;
    private SiteTypeEnum siteType;
    private String resourcePattern;
    private ResourceHttpMethodEnum httpMethod;

    public static ResourceExistsDto of(Long id, SiteTypeEnum siteType, String resourcePattern, ResourceHttpMethodEnum httpMethod) {
        return ResourceExistsDto.builder()
                .id(id)
                .siteType(siteType)
                .resourcePattern(resourcePattern)
                .httpMethod(httpMethod)
                .build();
    }
}
