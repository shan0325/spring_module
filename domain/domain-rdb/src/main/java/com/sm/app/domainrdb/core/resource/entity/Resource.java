package com.sm.app.domainrdb.core.resource.entity;


import com.sm.app.domainrdb.core.common.entity.BaseEntity;
import com.sm.app.domainrdb.core.common.enums.SiteTypeEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceHttpMethodEnum;
import com.sm.app.domainrdb.core.resource.enums.ResourceTypeEnum;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Resource extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SiteTypeEnum siteType;

    @Column(nullable = false, length = 50)
    private String resourceName;

    @Column(nullable = false, length = 255)
    private String resourcePattern;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ResourceHttpMethodEnum httpMethod;

    @Column(nullable = false)
    private Integer orderNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ResourceTypeEnum resourceType;

    //==수정 메서드==//
    public void modifyResource(String resourceName, String resourcePattern, ResourceHttpMethodEnum httpMethod, Integer orderNum) {
        this.resourceName = resourceName;
        this.resourcePattern = resourcePattern;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
    }
}
