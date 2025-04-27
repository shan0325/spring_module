package com.sm.app.admin.web.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import com.sm.app.domainrdb.core.admin.repository.query.SearchAdmin;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AdminsResponseDto {
    private Long id;
    private String adminId;
    private String name;
    private String email;
    private AdminStatusEnum status;
    private String roleName;
    private String roleDesc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    public static AdminsResponseDto from(SearchAdmin searchAdmin) {
        return AdminsResponseDto.builder()
                .id(searchAdmin.getId())
                .adminId(searchAdmin.getAdminId())
                .name(searchAdmin.getName())
                .email(searchAdmin.getEmail())
                .status(searchAdmin.getStatus())
                .roleName(searchAdmin.getRoleName())
                .roleDesc(searchAdmin.getRoleDesc())
                .regDate(searchAdmin.getRegDate())
                .build();
    }
}
