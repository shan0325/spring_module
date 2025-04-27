package com.sm.app.admin.web.admin.dto;

import com.sm.app.domainrdb.core.admin.entity.Admin;
import com.sm.app.domainrdb.core.role.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class AdminCreateRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String adminId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "권한은 필수 입력 값입니다.")
    private String roleName;

    public Admin toAdmin(PasswordEncoder passwordEncoder, Role role) {
        return Admin.builder()
                .adminId(this.getAdminId())
                .password(passwordEncoder.encode(this.getPassword()))
                .name(this.getName())
                .email(this.getEmail())
                .role(role)
                .build();
    }
}
