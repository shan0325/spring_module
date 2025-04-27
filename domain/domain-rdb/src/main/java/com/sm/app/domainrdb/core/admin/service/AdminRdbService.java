package com.sm.app.domainrdb.core.admin.service;

import com.sm.app.domainrdb.core.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdminRdbService {
    private final AdminRepository adminRepository;

}
