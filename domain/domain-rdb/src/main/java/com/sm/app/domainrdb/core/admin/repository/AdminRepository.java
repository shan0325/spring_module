package com.sm.app.domainrdb.core.admin.repository;

import com.sm.app.domainrdb.core.admin.entity.Admin;
import com.sm.app.domainrdb.core.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCusom {

    @Query("SELECT a FROM Admin a JOIN FETCH a.role WHERE a.adminId = :adminId")
    Optional<Admin> findByAdminIdWithRole(@Param("adminId") String adminId);

    boolean existsByAdminId(String adminId);

    boolean existsByRole(Role role);

    Optional<Admin> findByAdminId(String adminId);
}
