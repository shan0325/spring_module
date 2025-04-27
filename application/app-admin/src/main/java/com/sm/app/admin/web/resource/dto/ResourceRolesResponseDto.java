package com.sm.app.admin.web.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.ResourceRole;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ResourceRolesResponseDto {
    private Long id;
    private Long resourceId;
    private SiteTypeEnum siteType;
    private String resourceName;
    private String resourcePattern;
    private ResourceHttpMethodEnum httpMethod;
    private Integer orderNum;
    private ResourceTypeEnum resourceType;
    private String RegId;
    private String modId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    private Long roleId;
    private String roleName;
    private String roleDesc;

    public static ResourceRolesResponseDto from(ResourceRole resourceRole) {
        return ResourceRolesResponseDto.builder()
                .id(resourceRole.getId())
                .resourceId(resourceRole.getResource().getId())
                .siteType(resourceRole.getResource().getSiteType())
                .resourceName(resourceRole.getResource().getResourceName())
                .resourcePattern(resourceRole.getResource().getResourcePattern())
                .httpMethod(resourceRole.getResource().getHttpMethod())
                .orderNum(resourceRole.getResource().getOrderNum())
                .resourceType(resourceRole.getResource().getResourceType())
                .RegId(resourceRole.getResource().getRegId())
                .modId(resourceRole.getResource().getModId())
                .regDate(resourceRole.getResource().getRegDate())
                .modDate(resourceRole.getResource().getModDate())
                .roleId(resourceRole.getRole().getId())
                .roleName(resourceRole.getRole().getRoleName())
                .roleDesc(resourceRole.getRole().getRoleDesc())
                .build();
    }
}
