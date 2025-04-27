package com.sm.app.domainrdb.core.resource.repository.resourcerole;

import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.entity.ResourceRole;
import com.sm.app.domainrdb.core.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRoleRepository extends JpaRepository<ResourceRole, Long>, ResourceRoleRepositoryCustom {
    boolean existsByRole(Role role);

    boolean existsByResource(Resource resource);

    List<ResourceRole> findByResourceAndRole(Resource resource, Role role);
}
