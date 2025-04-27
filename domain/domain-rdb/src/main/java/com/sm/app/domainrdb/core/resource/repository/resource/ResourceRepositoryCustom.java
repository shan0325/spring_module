package com.sm.app.domainrdb.core.resource.repository.resource;

import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.repository.query.ResourceExistsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceRepositoryCustom {
    boolean existsResource(ResourceExistsDto resourceExistsDto);

    List<Resource> searchResources(SiteTypeEnum siteType, Pageable pageable, String search);

    Long countResources(SiteTypeEnum siteType, String search);
}
