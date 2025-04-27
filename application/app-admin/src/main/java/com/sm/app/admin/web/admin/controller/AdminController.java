package com.sm.app.admin.web.admin.controller;

import com.sm.app.admin.config.security.bean.LoginInfo;
import com.sm.app.admin.web.admin.dto.*;
import com.sm.app.admin.web.admin.service.AdminService;
import com.sm.app.admin.web.role.service.RoleService;
import com.sm.app.domainrdb.core.admin.enums.AdminStatusEnum;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;

    @GetMapping("/admins")
    public String admins() {
        return "pages/admin/admins";
    }

    @GetMapping("/api/admins")
    public ResponseEntity<Page<AdminsResponseDto>> getAdmins(
            @ModelAttribute SearchAdminRequestDto searchAdminRequestDto, Pageable pageable) {
        return ResponseEntity.ok(adminService.getAdmins(searchAdminRequestDto, pageable));
    }

    @GetMapping("/admins/{id}")
    public String getAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("admin", adminService.getAdmin(id));
        model.addAttribute("roles", roleService.getRolesBySiteType(SiteTypeEnum.ADMIN));
        model.addAttribute("status", AdminStatusEnum.values());
        return "pages/admin/modify";
    }

    @PutMapping("/api/admins/{id}")
    public ResponseEntity<Object> modifyAdmin(@PathVariable Long id,
                                              @RequestBody @Valid AdminModifyRequestDto adminModifyRequestDto) {
        adminService.modifyAdmin(id, adminModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admins/create")
    public String adminCreate(Model model) {
        model.addAttribute("roles", roleService.getRolesBySiteType(SiteTypeEnum.ADMIN));
        return "pages/admin/create";
    }

    @GetMapping("/api/admins/is-duplicated-admin-id/{adminId}")
    public ResponseEntity<Boolean> isDuplicatedAdminId(@PathVariable String adminId) {
        return ResponseEntity.ok(adminService.isDuplicatedAdminId(adminId));
    }

    @PostMapping("/api/admins")
    public ResponseEntity<Object> createAdmin(@RequestBody @Valid AdminCreateRequestDto adminCreateRequestDto) {
        adminService.createAdmin(adminCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admins/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/admins/password-change")
    public ResponseEntity<Object> passwordChange(@AuthenticationPrincipal LoginInfo loginInfo,
                                                 @RequestBody @Valid AdminPasswordChangeRequestDto adminPasswordChangeRequestDto) {
        adminService.passwordChange(loginInfo.getMemberId(), adminPasswordChangeRequestDto);
        return ResponseEntity.ok().build();
    }
}
