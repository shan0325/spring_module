package com.sm.app.admin.web.role.dto;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleResponseDto {
    private Long id;
    private SiteTypeEnum siteType;
    private String roleName;
    private String roleDesc;
    private String parentRoleName;
}
