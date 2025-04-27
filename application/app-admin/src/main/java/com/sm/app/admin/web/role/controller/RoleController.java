package com.sm.app.admin.web.role.controller;

import com.sm.app.admin.web.role.dto.RoleCreateRequestDto;
import com.sm.app.admin.web.role.dto.RoleModifyRequestDto;
import com.sm.app.admin.web.role.dto.RoleResponseDto;
import com.sm.app.admin.web.role.service.RoleService;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles/site-type/{siteType}")
    public String roles(@PathVariable SiteTypeEnum siteType, Model model) {
        model.addAttribute("siteType", siteType);
        return "pages/role/roles";
    }

    @GetMapping("/api/roles/site-type/{siteType}")
    public ResponseEntity<List<RoleResponseDto>> getRoles(@PathVariable SiteTypeEnum siteType) {
        return ResponseEntity.ok(roleService.getRolesBySiteType(siteType));
    }

    @GetMapping("/roles/{id}")
    public String getRole(@PathVariable Long id, Model model) {
        model.addAttribute("modifyDatas", roleService.getModifyDatas(id));
        return "pages/role/modify";
    }

    @PutMapping("/api/roles/{id}")
    public ResponseEntity<Object> modifyRole(@PathVariable Long id,
                                             @RequestBody @Valid RoleModifyRequestDto roleModifyRequestDto) {
        roleService.modifyRole(id, roleModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/roles/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles/site-type/{siteType}/create")
    public String roleCreate(@PathVariable SiteTypeEnum siteType, Model model) {
        model.addAttribute("siteType", siteType);
        model.addAttribute("roles", roleService.getRolesBySiteType(siteType));
        return "pages/role/create";
    }

    @PostMapping("/api/roles")
    public ResponseEntity<Object> createRole(@RequestBody @Valid RoleCreateRequestDto roleCreateRequestDto) {
        roleService.createRole(roleCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/roles/{id}/resources")
    public ResponseEntity<Object> addResources(@PathVariable(name = "id") Long roleId,
                                               @RequestBody List<Long> resourceIds) {
        roleService.addResources(roleId, resourceIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/roles/resources-roles/{id}")
    public ResponseEntity<Object> deleteResources(@PathVariable(name = "id") Long resourceRoleId) {
        roleService.deleteResource(resourceRoleId);
        return ResponseEntity.ok().build();
    }
}
