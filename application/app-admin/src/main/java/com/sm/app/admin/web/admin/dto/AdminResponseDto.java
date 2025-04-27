package com.sm.app.admin.web.admin.dto;

import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import com.sm.app.domainrdb.core.admin.repository.query.AdminRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AdminResponseDto {

    private Long id;
    private String adminId;
    private String name;
    private String email;
    private AdminStatusEnum status;
    private String roleName;
    private String roleDesc;
    private LocalDateTime regDate;

    public static AdminResponseDto from(AdminRole adminRole) {
        return AdminResponseDto.builder()
                .id(adminRole.getId())
                .adminId(adminRole.getAdminId())
                .name(adminRole.getName())
                .email(adminRole.getEmail())
                .status(adminRole.getStatus())
                .roleName(adminRole.getRoleName())
                .roleDesc(adminRole.getRoleDesc())
                .regDate(adminRole.getRegDate())
                .build();
    }
}
