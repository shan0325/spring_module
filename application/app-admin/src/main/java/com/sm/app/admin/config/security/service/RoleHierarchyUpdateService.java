package com.sm.app.admin.config.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleHierarchyUpdateService {

    private final RoleHierarchyImpl roleHierarchy;
    private final RoleHierarchyService roleHierarchyService;

    public void reload() {
        roleHierarchy.setHierarchy(roleHierarchyService.getAllRoleHierarchy());
    }
}
