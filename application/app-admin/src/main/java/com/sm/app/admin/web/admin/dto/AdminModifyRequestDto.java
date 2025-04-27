package com.sm.app.admin.web.admin.dto;

import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AdminModifyRequestDto {
    @Setter
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotNull(message = "상태는 필수 입력 값입니다.")
    private AdminStatusEnum status;

    @NotBlank(message = "권한은 필수 입력 값입니다.")
    private String roleName;
}
