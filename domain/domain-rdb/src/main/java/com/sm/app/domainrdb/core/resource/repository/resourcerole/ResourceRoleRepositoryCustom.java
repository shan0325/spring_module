package com.sm.app.domainrdb.core.resource.repository.resourcerole;


import com.sm.app.domainrdb.core.resource.entity.ResourceRole;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import com.sm.app.domainrdb.core.resource.repository.query.SecurityResourceRoleDto;

import java.util.List;

public interface ResourceRoleRepositoryCustom {
    List<SecurityResourceRoleDto> findByResourceType(ResourceTypeEnum resourceType);

    List<ResourceRole> findByRoleId(Long roleId);
}
