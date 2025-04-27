package com.sm.app.domainrdb.core.resource.repository.resource;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.repository.query.ResourceExistsDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sm.app.domainrdb.core.resource.entity.QResource.resource;

@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsResource(ResourceExistsDto resourceExistsDto) {
        return queryFactory.selectOne()
                .from(resource)
                .where(
                    idNotEq(resourceExistsDto.getId()),
                    resource.siteType.eq(resourceExistsDto.getSiteType())
                        .and(resource.resourcePattern.eq(resourceExistsDto.getResourcePattern()))
                        .and(
                            resource.httpMethod.eq(ResourceHttpMethodEnum.ALL)
                                .or(resource.httpMethod.eq(resourceExistsDto.getHttpMethod()))
                        )
                )
                .fetchFirst() != null;
    }

    @Override
    public List<Resource> searchResources(SiteTypeEnum siteType, Pageable pageable, String search) {
        return queryFactory
                .selectFrom(resource)
                .where(
                    resource.siteType.eq(siteType)
                    .and(resourceNameOrResourcePatternContains(search))
                )
                .orderBy(resource.orderNum.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countResources(SiteTypeEnum siteType, String search) {
        return queryFactory
                .select(resource.count())
                .from(resource)
                .where(
                    resource.siteType.eq(siteType)
                    .and(resourceNameOrResourcePatternContains(search))
                )
                .fetchOne();
    }

    private BooleanExpression idNotEq(Long id) {
        return id != null ? resource.id.ne(id) : null;
    }

    private BooleanExpression resourceNameOrResourcePatternContains(String search) {
        return !StringUtils.isBlank(search)
                ? resource.resourceName.contains(search)
                    .or(resource.resourcePattern.contains(search))
                : null;
    }
}
