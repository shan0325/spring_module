package com.sm.app.domainrdb.core.admin.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminRole {
    private Long id;
    private String adminId;
    private String name;
    private String email;
    private AdminStatusEnum status;
    private String roleName;
    private String roleDesc;
    private LocalDateTime regDate;

    @QueryProjection
    public AdminRole(Long id, String adminId, String name, String email, AdminStatusEnum status, String roleName, String roleDesc, LocalDateTime regDate) {
        this.id = id;
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.status = status;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.regDate = regDate;
    }
}
