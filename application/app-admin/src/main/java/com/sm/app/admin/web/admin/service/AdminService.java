package com.sm.app.admin.web.admin.service;

import com.sm.app.admin.web.admin.dto.*;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.admin.dto.SearchAdminDto;
import com.sm.app.domainrdb.core.admin.entity.Admin;
import com.sm.app.domainrdb.core.admin.repository.AdminRepository;
import com.sm.app.domainrdb.core.admin.repository.query.AdminRole;
import com.sm.app.domainrdb.core.admin.repository.query.SearchAdmin;
import com.sm.app.domainrdb.core.role.entity.Role;
import com.sm.app.domainrdb.core.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public Page<AdminsResponseDto> getAdmins(SearchAdminRequestDto searchAdminRequestDto, Pageable pageable) {
        SearchAdminDto searchAdminDto = SearchAdminDto.of(searchAdminRequestDto.getSearchType(), searchAdminRequestDto.getSearch());

        List<SearchAdmin> admins = adminRepository.searchAdmins(searchAdminDto, pageable);
        Long totalCount = adminRepository.countAdmins(searchAdminDto);
        return new PageImpl<>(admins.stream().map(AdminsResponseDto::from).toList(), pageable, totalCount);
    }

    public AdminResponseDto getAdmin(Long id) {
        AdminRole adminRole = adminRepository.findAdminById(id)
                .orElseThrow(() -> new ServiceException("관리자가 존재하지 않습니다."));
        return AdminResponseDto.from(adminRole);
    }

    @Transactional
    public void modifyAdmin(Long id, AdminModifyRequestDto adminModifyRequestDto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ServiceException("관리자가 존재하지 않습니다."));

        Role role = roleRepository.findByRoleName(adminModifyRequestDto.getRoleName())
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        if (StringUtils.isNotBlank(adminModifyRequestDto.getPassword())) {
            adminModifyRequestDto.setPassword(passwordEncoder.encode(adminModifyRequestDto.getPassword()));
        }

        admin.modifyAdmin(adminModifyRequestDto.getPassword(),
                adminModifyRequestDto.getName(), adminModifyRequestDto.getEmail(),
                adminModifyRequestDto.getStatus(), role);
    }

    @Transactional
    public void createAdmin(AdminCreateRequestDto adminCreateRequestDto) {
        boolean isExistsAdminId = adminRepository.existsByAdminId(adminCreateRequestDto.getAdminId());
        if (isExistsAdminId) {
            throw new ServiceException("이미 존재하는 관리자 아이디입니다.");
        }

        Role role = roleRepository.findByRoleName(adminCreateRequestDto.getRoleName())
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        Admin admin = adminCreateRequestDto.toAdmin(passwordEncoder, role);
        adminRepository.save(admin);
    }

    @Transactional
    public void passwordChange(String adminId, AdminPasswordChangeRequestDto adminPasswordChangeRequestDto) {
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new ServiceException("관리자가 존재하지 않습니다."));

        if (!passwordEncoder.matches(adminPasswordChangeRequestDto.getPrevPassword(), admin.getPassword())) {
            throw new ServiceException("기존 비밀번호가 일치하지 않습니다.");
        }

        if (!adminPasswordChangeRequestDto.getNewPassword().equals(adminPasswordChangeRequestDto.getConfirmNewPassword())) {
            throw new ServiceException("변경 비밀번호와 변경 비밀번호 확인이 일치하지 않습니다.");
        }

        admin.changePassword(adminPasswordChangeRequestDto.getNewPassword());
    }

    @Transactional
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ServiceException("관리자가 존재하지 않습니다."));

        adminRepository.delete(admin);
    }

    public Boolean isDuplicatedAdminId(String adminId) {
        return adminRepository.existsByAdminId(adminId);
    }
}
