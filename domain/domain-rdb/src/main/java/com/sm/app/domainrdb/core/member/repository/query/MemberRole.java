package com.sm.app.domainrdb.core.member.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class MemberRole {

    private Long id;
    private String memberId;
    private String name;
    private String roleName;
    private String roleDesc;
    private LocalDateTime regDate;

    @QueryProjection
    public MemberRole(Long id, String memberId, String name, String roleName, String roleDesc, LocalDateTime regDate) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.regDate = regDate;
    }
}
