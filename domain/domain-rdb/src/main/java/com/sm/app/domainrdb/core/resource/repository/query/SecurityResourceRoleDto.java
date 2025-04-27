package com.sm.app.domainrdb.core.resource.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import lombok.Getter;

@Getter
public class SecurityResourceRoleDto {
    private String resourcePattern;
    private ResourceHttpMethodEnum httpMethod;
    private String roleName;

    @QueryProjection
    public SecurityResourceRoleDto(String resourcePattern, ResourceHttpMethodEnum httpMethod, String roleName) {
        this.resourcePattern = resourcePattern;
        this.httpMethod = httpMethod;
        this.roleName = roleName;
    }
}
