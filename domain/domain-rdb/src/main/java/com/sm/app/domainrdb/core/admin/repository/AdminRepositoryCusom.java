package com.sm.app.domainrdb.core.admin.repository;

import com.sm.app.domainrdb.core.admin.dto.SearchAdminDto;
import com.sm.app.domainrdb.core.admin.repository.query.AdminRole;
import com.sm.app.domainrdb.core.admin.repository.query.SearchAdmin;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdminRepositoryCusom {
    List<SearchAdmin> searchAdmins(SearchAdminDto searchAdminDto, Pageable pageable);

    Long countAdmins(SearchAdminDto searchAdminDto);

    Optional<AdminRole> findAdminById(Long id);
}
