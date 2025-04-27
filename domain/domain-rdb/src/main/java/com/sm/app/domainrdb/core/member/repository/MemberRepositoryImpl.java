package com.sm.app.domainrdb.core.member.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.member.repository.query.MemberRole;
import com.sm.app.domainrdb.core.member.repository.query.MemberRoles;
import com.sm.app.domainrdb.core.member.repository.query.QMemberRole;
import com.sm.app.domainrdb.core.member.repository.query.QMemberRoles;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.sm.app.domainrdb.core.member.entity.QMember.member;
import static com.sm.app.domainrdb.core.role.entity.QRole.role;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberRoles> findMembers(String searchType, String search, Pageable pageable) {
        return queryFactory
                .select(new QMemberRoles(
                        member.id,
                        member.memberId,
                        member.name,
                        role.roleName,
                        role.roleDesc,
                        member.regDate
                ))
                .from(member)
                .join(member.role, role)
                .where(memberIdOrNameContains(searchType, search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long findMembersTotalCount(String searchType, String search) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(memberIdOrNameContains(searchType, search))
                .fetchOne();
    }

    private Predicate memberIdOrNameContains(String searchType, String search) {
        if (StringUtils.isBlank(searchType)) return null;

        if ("id".equals(searchType)) {
            return member.memberId.contains(search);
        } else if ("name".equals(searchType)) {
            return member.name.contains(search);
        }
        return null;
    }

    @Override
    public Optional<MemberRole> findMemberById(Long id) {
        return Optional.ofNullable(queryFactory
                .select(new QMemberRole(
                        member.id,
                        member.memberId,
                        member.name,
                        role.roleName,
                        role.roleDesc,
                        member.regDate
                ))
                .from(member)
                .join(member.role, role)
                .where(member.id.eq(id))
                .fetchOne());
    }
}
