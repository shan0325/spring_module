package com.sm.app.domainrdb.core.role.repository;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findBySiteType(SiteTypeEnum siteType);

    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findBySiteTypeAndRoleName(SiteTypeEnum siteType, String roleName);
}
