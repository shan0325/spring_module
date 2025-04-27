package com.sm.app.domainrdb.core.admin.entity;

import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import com.sm.app.domainrdb.core.common.entity.BaseEntity;
import com.sm.app.domainrdb.core.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String adminId;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 80)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AdminStatusEnum status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime passwordDate;

    @Column(nullable = false)
    private LocalDateTime loginDate;

    //==수정 메서드==//
    public void modifyAdmin(String encPassword, String name, String email, AdminStatusEnum status, Role role) {
        if (!StringUtils.isBlank(encPassword)) {
            this.password = encPassword;
            this.passwordDate = LocalDateTime.now();
        }

        this.name = name;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public void changePassword(String newEncPassword) {
        this.password = newEncPassword;
        this.passwordDate = LocalDateTime.now();
    }

    // 비밀번호 만료 여부(90일)
    public boolean isPasswordExpired() {
        if (this.passwordDate == null) return false;
        return getPasswordExpireDays().isBefore(LocalDateTime.now());
    }

    // 비밀번호 만료 알림 여부(만료 10일 전)
    public boolean isPasswordExpiredAlert() {
        if (this.passwordDate == null) return false;
        return getPasswordExpireDays().isBefore(LocalDateTime.now().plusDays(10));
    }

    public LocalDateTime getPasswordExpireDays() {
        final int PASSWORD_EXPIRE_DAYS = 90;
        return passwordDate.plusDays(PASSWORD_EXPIRE_DAYS);
    }

    public void setLastLoginDate() {
        this.loginDate = LocalDateTime.now();
    }
}
