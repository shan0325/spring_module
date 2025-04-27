package com.sm.app.admin.web.role.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RoleModifyRequestDto {
    @NotBlank(message = "권한 설명은 필수 입력 값입니다.")
    private String roleDesc;

    private String parentRoleName;
}
