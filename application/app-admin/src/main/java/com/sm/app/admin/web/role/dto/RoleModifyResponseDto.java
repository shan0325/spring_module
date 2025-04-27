package com.sm.app.admin.web.role.dto;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.role.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoleModifyResponseDto {
    private Long id;
    private SiteTypeEnum siteType;
    private String roleName;
    private String roleDesc;
    private String parentRoleName;
    private List<RoleResponseDto> roles;

    public RoleModifyResponseDto(Long id, SiteTypeEnum siteType, String roleName, String roleDesc, String parentRoleName, List<RoleResponseDto> roles) {
        this.id = id;
        this.siteType = siteType;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.parentRoleName = parentRoleName;
        this.roles = roles;
    }

    public static RoleModifyResponseDto of(Role role, List<RoleResponseDto> roles) {
        return RoleModifyResponseDto.builder()
                .id(role.getId())
                .siteType(role.getSiteType())
                .roleName(role.getRoleName())
                .roleDesc(role.getRoleDesc())
                .parentRoleName(role.getParentRoleName())
                .roles(roles)
                .build();
    }
}
