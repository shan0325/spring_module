package com.sm.app.admin.web.role.usecase;

import com.sm.app.admin.config.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.sm.app.admin.config.security.service.RoleHierarchyUpdateService;
import com.sm.app.admin.web.role.dto.RoleCreateRequestDto;
import com.sm.app.admin.web.role.dto.RoleModifyRequestDto;
import com.sm.app.admin.web.role.dto.RoleModifyResponseDto;
import com.sm.app.admin.web.role.dto.RoleResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.admin.repository.AdminRepository;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.member.repository.MemberRepository;
import com.sm.app.domainrdb.core.resource.entity.ResourceRole;
import com.sm.app.domainrdb.core.resource.repository.resource.ResourceRepository;
import com.sm.app.domainrdb.core.resource.repository.resourcerole.ResourceRoleRepository;
import com.sm.app.domainrdb.core.role.entity.Role;
import com.sm.app.domainrdb.core.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RoleUseCase {
    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceRoleRepository resourceRoleRepository;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final RoleHierarchyUpdateService roleHierarchyUpdateService;
    private final ModelMapper modelMapper;
//    private final RabbitMqScSecService rabbitMqScSecService;

    public List<RoleResponseDto> getRolesBySiteType(SiteTypeEnum siteTypeEnum) {
        List<Role> roles = roleRepository.findBySiteType(siteTypeEnum);
        return roles.stream().map(role -> modelMapper.map(role, RoleResponseDto.class)).toList();
    }

    public RoleModifyResponseDto getModifyDatas(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        List<RoleResponseDto> roles = roleRepository.findBySiteType(role.getSiteType()).stream()
                .filter(r -> !r.getId().equals(id))
                .map(r -> modelMapper.map(r, RoleResponseDto.class))
                .toList();

        return RoleModifyResponseDto.of(role, roles);
    }

    @Transactional
    public void modifyRole(Long id, RoleModifyRequestDto roleModifyRequestDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        boolean isChangedParentRoleName = role.isChangedParentRoleName(roleModifyRequestDto.getParentRoleName());
        role.modifyRole(roleModifyRequestDto.getRoleDesc(), roleModifyRequestDto.getParentRoleName());

        // 부모 권한이 변경된 경우 권한 계층을 다시 로드
        if (isChangedParentRoleName) {
            reloadSecurityRoleHierarchy(role.getSiteType());
        }
    }

    @Transactional
    public void createRole(RoleCreateRequestDto roleCreateRequestDto) {
        roleRepository.findBySiteTypeAndRoleName(roleCreateRequestDto.getSiteType(), roleCreateRequestDto.getRoleName())
                .ifPresent(role -> {
                    throw new ServiceException("이미 존재하는 권한입니다.");
                });

        Role role = Role.createRole(roleCreateRequestDto.getSiteType(), roleCreateRequestDto.getRoleName(),
                roleCreateRequestDto.getRoleDesc(), roleCreateRequestDto.getParentRoleName());

        roleRepository.save(role);

        reloadSecurityRoleHierarchy(role.getSiteType());
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        validateRoleDependencies(role);

        roleRepository.deleteById(id);
    }

    @Transactional
    public void addResources(Long roleId, List<Long> resourceIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ServiceException("권한이 존재하지 않습니다."));

        List<ResourceRole> addResourceRoles = resourceRepository.findAllById(resourceIds).stream()
                .filter(resource -> resourceRoleRepository.findByResourceAndRole(resource, role).isEmpty())
                .map(resource -> ResourceRole.createResourceRole(resource, role))
                .toList();

        resourceRoleRepository.saveAll(addResourceRoles);

        reloadSecurityMetadataSource(role.getSiteType());
    }

    @Transactional
    public void deleteResource(Long resourceRoleId) {
        ResourceRole resourceRole = resourceRoleRepository.findById(resourceRoleId)
                .orElseThrow(() -> new ServiceException("리소스 권한이 존재하지 않습니다."));

        resourceRoleRepository.deleteById(resourceRoleId);

        reloadSecurityMetadataSource(resourceRole.getResource().getSiteType());
    }

    // 시큐리티 권한 계층을 다시 로드
    private void reloadSecurityRoleHierarchy(SiteTypeEnum siteTypeEnum) {
        if (SiteTypeEnum.ADMIN.equals(siteTypeEnum)) {
            roleHierarchyUpdateService.reload();
        } else if(SiteTypeEnum.USER.equals(siteTypeEnum)) {
            /*rabbitMqScSecService.sendReqReload(RabbitMqMessageDto.builder()
                    .message(SecReloadEnum.ROLE_RELOAD.name())
                    .build());*/
        }
    }

    // 시큐리티 리소스 설정을 다시 로드
    private void reloadSecurityMetadataSource(SiteTypeEnum siteTypeEnum) {
        if (SiteTypeEnum.ADMIN.equals(siteTypeEnum)) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else if(SiteTypeEnum.USER.equals(siteTypeEnum)) {
            /*rabbitMqScSecService.sendReqReload(RabbitMqMessageDto.builder()
                    .message(SecReloadEnum.RESOURCE_RELOAD.name())
                    .build());*/
        }
    }

    private void validateRoleDependencies(Role role) {
        if (resourceRoleRepository.existsByRole(role)) {
            throw new ServiceException("권한에 할당된 리소스가 존재합니다.");
        }

        SiteTypeEnum siteType = role.getSiteType();
        boolean isExists = SiteTypeEnum.ADMIN.equals(siteType)
                ? adminRepository.existsByRole(role)
                : memberRepository.existsByRole(role);

        if (isExists) {
            throw new ServiceException("권한에 할당된 " + (SiteTypeEnum.ADMIN.equals(siteType) ? "관리자가" : "회원이") + " 존재합니다.");
        }
    }
}
