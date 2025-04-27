package com.sm.app.domainrdb.core.resource.repository.resource;

import com.sm.app.domainrdb.core.resource.entity.Resource;
import com.sm.app.domainrdb.core.resource.repository.resource.ResourceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceRepositoryCustom {

}
