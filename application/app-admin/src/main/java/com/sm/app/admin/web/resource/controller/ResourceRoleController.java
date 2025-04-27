package com.sm.app.admin.web.resource.controller;

import com.sm.app.admin.web.resource.dto.ResourceRolesResponseDto;
import com.sm.app.admin.web.resource.service.ResourceRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResourceRoleController {

    private final ResourceRoleService resourceRoleService;

    @GetMapping("/api/resource-roles/role/{id}")
    public ResponseEntity<List<ResourceRolesResponseDto>> getResourceRoles(@PathVariable(name = "id") Long roleId) {
        return ResponseEntity.ok(resourceRoleService.getResourceRoles(roleId));
    }

}
