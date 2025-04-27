package com.sm.app.domainrdb.core.member.repository.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberRoles {

    private Long id;
    private String memberId;
    private String name;
    private String roleName;
    private String roleDesc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @QueryProjection
    public MemberRoles(Long id, String memberId, String name, String roleName, String roleDesc, LocalDateTime regDate) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.regDate = regDate;
    }
}
