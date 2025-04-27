package com.sm.app.admin.config.security.service;

import com.sm.app.admin.config.security.SecurityConfig;
import com.sm.app.admin.config.security.bean.LoginInfo;
import com.sm.app.domainrdb.core.admin.entity.Admin;
import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import com.sm.app.domainrdb.core.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    /**
     * DB에서 회원 및 권한 정보 조회
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        Admin admin = adminRepository.findByAdminIdWithRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 회원 정보입니다. [" + username + "]"));

        String name = admin.getName();
        String password = admin.getPassword();
        String authority = SecurityConfig.ROLE_PREFIX + admin.getRole().getRoleName();
        AdminStatusEnum status = admin.getStatus();

        if (!status.equals(AdminStatusEnum.ACTIVE)) {
            throw new DisabledException("비활성화된 계정입니다. [" + username + "]");
        }

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(authority));

        return new LoginInfo(username, password, roles, authority, name);
    }
}
