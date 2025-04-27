package com.sm.app.admin.config.security.service;

import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import com.sm.app.domainrdb.core.resource.repository.query.SecurityResourceRoleDto;
import com.sm.app.domainrdb.core.resource.repository.resourcerole.ResourceRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static com.sm.app.admin.config.security.SecurityConfig.ROLE_PREFIX;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SecurityResourceService {

    private final ResourceRoleRepository resourceRoleRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        List<SecurityResourceRoleDto> resourceRoles = resourceRoleRepository.findByResourceType(ResourceTypeEnum.URL);
        return getResourceMap(resourceRoles, resource -> {
            String httpMethod = ResourceHttpMethodEnum.ALL.equals(resource.getHttpMethod()) ? null : resource.getHttpMethod().name();
            return new AntPathRequestMatcher(resource.getResourcePattern(), httpMethod);
        });
    }

    public LinkedHashMap<String, List<ConfigAttribute>> getMethodResourceList() {
        List<SecurityResourceRoleDto> resourceRoles = resourceRoleRepository.findByResourceType(ResourceTypeEnum.METHOD);
        return getResourceMap(resourceRoles, SecurityResourceRoleDto::getResourcePattern);
    }

    // 사용 안함
    public LinkedHashMap<String, List<ConfigAttribute>> getPointcutResourceList() {
        return new LinkedHashMap<>();
    }

    private <T> LinkedHashMap<T, List<ConfigAttribute>> getResourceMap(List<SecurityResourceRoleDto> resourceRoles, Function<SecurityResourceRoleDto, T> keyMapper) {
        LinkedHashMap<T, List<ConfigAttribute>> result = new LinkedHashMap<>();

        for (SecurityResourceRoleDto resource : resourceRoles) {
            T key = keyMapper.apply(resource);
            if (result.containsKey(key)) {
                continue;
            }

            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resourceRoles.stream()
                    .filter(r -> Objects.equals(r.getResourcePattern(), resource.getResourcePattern()))
                    .forEach(r -> configAttributeList.add(new SecurityConfig(ROLE_PREFIX + r.getRoleName())));

            result.put(key, configAttributeList);
        }
        return result;
    }
}
