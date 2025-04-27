package com.sm.app.admin.web.resource.usecase;

import com.sm.app.admin.web.resource.dto.ResourceRolesResponseDto;
import com.sm.app.domainrdb.core.resource.repository.resourcerole.ResourceRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ResourceRoleUseCase {

    private final ResourceRoleRepository resourceRoleRepository;

    public List<ResourceRolesResponseDto> getResourceRoles(Long roleId) {
        return resourceRoleRepository.findByRoleId(roleId).stream()
                .map(ResourceRolesResponseDto::from)
                .toList();
    }
}
