package com.sm.app.admin.web.admin.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AdminPasswordChangeRequestDto {
    @NotBlank(message = "이전 비밀번호는 필수 입력 값입니다.")
    private String prevPassword;

    @NotBlank(message = "변경 비밀번호는 필수 입력 값입니다.")
    private String newPassword;

    @NotBlank(message = "변경 비밀번호 확인은 필수 입력 값입니다.")
    private String confirmNewPassword;
}
