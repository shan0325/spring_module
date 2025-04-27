package com.sm.app.admin.web.member.service;

import com.sm.app.admin.web.member.dto.MembersRequestDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.member.repository.MemberRepository;
import com.sm.app.domainrdb.core.member.repository.query.MemberRole;
import com.sm.app.domainrdb.core.member.repository.query.MemberRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<MemberRoles> getMembers(MembersRequestDto membersRequestDto, Pageable pageable) {
        String searchType = membersRequestDto.getSearchType();
        String search = membersRequestDto.getSearch();

        List<MemberRoles> members = memberRepository.findMembers(searchType, search, pageable);
        Long totalCount = memberRepository.findMembersTotalCount(searchType, search);
        return new PageImpl<>(members, pageable, totalCount);
    }

    public MemberRole getMember(Long id) {
        return memberRepository.findMemberById(id)
                .orElseThrow(() -> new ServiceException("회원이 존재하지 않습니다."));
    }
}
