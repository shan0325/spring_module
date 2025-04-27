package com.sm.app.domainrdb.core.admin.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.admin.dto.SearchAdminDto;
import com.sm.app.domainrdb.core.admin.repository.query.AdminRole;
import com.sm.app.domainrdb.core.admin.repository.query.QAdminRole;
import com.sm.app.domainrdb.core.admin.repository.query.QSearchAdmin;
import com.sm.app.domainrdb.core.admin.repository.query.SearchAdmin;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.sm.app.domainrdb.core.admin.entity.QAdmin.admin;
import static com.sm.app.domainrdb.core.role.entity.QRole.role;


@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCusom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchAdmin> searchAdmins(SearchAdminDto searchAdminDto, Pageable pageable) {
        return queryFactory
                .select(new QSearchAdmin(
                        admin.id,
                        admin.adminId,
                        admin.name,
                        admin.email,
                        admin.status,
                        role.roleName,
                        role.roleDesc,
                        admin.regDate
                ))
                .from(admin)
                .join(admin.role, role)
                .where(adminIdOrNameContains(searchAdminDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countAdmins(SearchAdminDto searchAdminDto) {
        return queryFactory
                .select(admin.count())
                .from(admin)
                .where(adminIdOrNameContains(searchAdminDto))
                .fetchOne();
    }

    private Predicate adminIdOrNameContains(SearchAdminDto searchAdminDto) {
        String searchType = searchAdminDto.getSearchType();
        if (StringUtils.isBlank(searchType)) return null;

        if ("id".equals(searchType)) {
            return admin.adminId.contains(searchAdminDto.getSearch());
        } else if ("name".equals(searchType)) {
            return admin.name.contains(searchAdminDto.getSearch());
        }
        return null;
    }

    @Override
    public Optional<AdminRole> findAdminById(Long id) {
        AdminRole adminRole = queryFactory
                .select(new QAdminRole(
                        admin.id,
                        admin.adminId,
                        admin.name,
                        admin.email,
                        admin.status,
                        role.roleName,
                        role.roleDesc,
                        admin.regDate
                ))
                .from(admin)
                .join(admin.role, role)
                .where(admin.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(adminRole);
    }
}
