package com.sm.app.admin.config.security.service;

import com.sm.app.admin.config.security.SecurityConfig;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

    private final RoleRepository roleRepository;

    public String getAllRoleHierarchy() {
        String roleHierarchy = roleRepository.findBySiteType(SiteTypeEnum.ADMIN).stream()
                .filter(role -> role.getParentRoleName() != null)
                .map(role -> SecurityConfig.ROLE_PREFIX + role.getParentRoleName() + " > " +
                        SecurityConfig.ROLE_PREFIX + role.getRoleName())
                .collect(Collectors.joining("\n"));

        log.info("RoleHierarchy: \n{}", roleHierarchy);
        return roleHierarchy;
    }
}
