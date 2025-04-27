package com.sm.app.domainrdb.core.member.repository;

import com.sm.app.domainrdb.core.member.repository.query.MemberRole;
import com.sm.app.domainrdb.core.member.repository.query.MemberRoles;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    List<MemberRoles> findMembers(String searchType, String search, Pageable pageable);

    Long findMembersTotalCount(String searchType, String search);

    Optional<MemberRole> findMemberById(Long id);
}
