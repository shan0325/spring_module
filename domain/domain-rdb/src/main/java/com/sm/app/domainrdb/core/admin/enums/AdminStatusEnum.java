package com.sm.app.domainrdb.core.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminStatusEnum {
    ACTIVE("활성화"),
    SUSPENDED("정지");

    private final String statusName;
}
