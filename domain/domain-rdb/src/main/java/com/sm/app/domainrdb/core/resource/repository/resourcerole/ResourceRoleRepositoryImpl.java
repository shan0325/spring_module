package com.sm.app.domainrdb.core.resource.repository.resourcerole;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.ResourceRole;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import com.sm.app.domainrdb.core.resource.repository.query.QSecurityResourceRoleDto;
import com.sm.app.domainrdb.core.resource.repository.query.SecurityResourceRoleDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sm.app.domainrdb.core.resource.entity.QResource.resource;
import static com.sm.app.domainrdb.core.resource.entity.QResourceRole.resourceRole;
import static com.sm.app.domainrdb.core.role.entity.QRole.role;


@RequiredArgsConstructor
public class ResourceRoleRepositoryImpl implements ResourceRoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SecurityResourceRoleDto> findByResourceType(ResourceTypeEnum resourceType) {
        return queryFactory
                .select(new QSecurityResourceRoleDto(
                        resource.resourcePattern,
                        resource.httpMethod,
                        role.roleName
                ))
                .from(resourceRole)
                .join(resourceRole.resource, resource)
                    .on(resource.siteType.eq(SiteTypeEnum.ADMIN))
                .join(resourceRole.role, role)
                    .on(role.siteType.eq(SiteTypeEnum.ADMIN))
                .where(resource.resourceType.eq(resourceType))
                .orderBy(resource.orderNum.asc())
                .fetch();
    }

    @Override
    public List<ResourceRole> findByRoleId(Long roleId) {
        return queryFactory
                .selectFrom(resourceRole)
                .join(resourceRole.resource, resource).fetchJoin()
                .join(resourceRole.role, role).fetchJoin()
                .where(resourceRole.role.id.eq(roleId))
                .orderBy(resource.orderNum.asc())
                .fetch();
    }

}
