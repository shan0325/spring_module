package com.sm.app.domainrdb.core.role.service;

import com.sm.app.domainrdb.core.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RoleRdbService {
    private final RoleRepository roleRepository;

}
