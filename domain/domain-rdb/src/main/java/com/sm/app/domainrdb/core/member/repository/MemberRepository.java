package com.sm.app.domainrdb.core.member.repository;

import com.sm.app.domainrdb.core.member.entity.Member;
import com.sm.app.domainrdb.core.member.repository.MemberRepositoryCustom;
import com.sm.app.domainrdb.core.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    boolean existsByRole(Role role);
}
