package com.sm.app.domainrdb.core.role.entity;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SiteTypeEnum siteType;

    @Column(nullable = false, length = 50)
    private String roleName;

    private String roleDesc;

    private String parentRoleName;

    @Builder(access = AccessLevel.PRIVATE)
    public Role(Long id, SiteTypeEnum siteType, String roleName, String roleDesc, String parentRoleName) {
        this.id = id;
        this.siteType = siteType;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.parentRoleName = parentRoleName;
    }

    //==수정 메서드==//
    public void modifyRole(String roleDesc, String parentRoleName) {
        this.roleDesc = roleDesc;
        this.parentRoleName = StringUtils.defaultIfBlank(parentRoleName, null);
    }

    //==생성 메서드==//
    public static Role createRole(SiteTypeEnum siteType, String roleName, String roleDesc, String parentRoleName) {
        return Role.builder()
                .siteType(siteType)
                .roleName(roleName)
                .roleDesc(roleDesc)
                .parentRoleName(parentRoleName)
                .build();
    }

    public boolean isChangedParentRoleName(String parentRoleName) {
        return !Objects.equals(StringUtils.defaultString(this.parentRoleName), parentRoleName);
    }
}
