package com.sm.app.admin.web.role.dto;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class RoleCreateRequestDto {

    @NotNull(message = "사이트 타입은 필수 입력 값입니다.")
    private SiteTypeEnum siteType;

    @NotBlank(message = "권한명은 필수 입력 값입니다.")
    private String roleName;

    @NotBlank(message = "권한 설명은 필수 입력 값입니다.")
    private String roleDesc;

    private String parentRoleName;
}
