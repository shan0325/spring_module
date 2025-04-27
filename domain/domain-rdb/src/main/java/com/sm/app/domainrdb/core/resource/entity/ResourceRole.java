package com.sm.app.domainrdb.core.resource.entity;


import com.sm.app.domainrdb.core.role.entity.Role;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class ResourceRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder(access = AccessLevel.PRIVATE)
    public ResourceRole(Long id, Resource resource, Role role) {
        this.id = id;
        this.resource = resource;
        this.role = role;
    }

    //== 생성 메서드 ==//
    public static ResourceRole createResourceRole(Resource resource, Role role) {
        return ResourceRole.builder()
                .resource(resource)
                .role(role)
                .build();
    }
}
