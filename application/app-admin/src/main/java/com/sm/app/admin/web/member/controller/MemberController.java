package com.sm.app.admin.web.member.controller;

import com.sm.app.admin.web.member.dto.MembersRequestDto;
import com.sm.app.admin.web.member.usecase.MemberUseCase;
import com.sm.app.domainrdb.core.member.repository.query.MemberRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberUseCase memberUseCase;

    @GetMapping("/members")
    public String members() {
        return "pages/member/members";
    }

    @GetMapping("/api/members")
    public ResponseEntity<Page<MemberRoles>> getMembers(
            @ModelAttribute MembersRequestDto membersRequestDto, Pageable pageable) {
        return ResponseEntity.ok(memberUseCase.getMembers(membersRequestDto, pageable));
    }

    @GetMapping("/members/{id}")
    public String getMember(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberUseCase.getMember(id));
        return "pages/member/member";
    }

}
