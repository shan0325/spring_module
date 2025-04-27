package com.sm.app.admin.config.security.handler;

import com.sm.app.admin.config.security.bean.LoginInfo;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.admin.entity.Admin;
import com.sm.app.domainrdb.core.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Form Login에서 로그인에 성공한 경우 동작하기 위해 SavedRequestAwareAuthenticationSuccessHandler를 상속받는 핸들러를 구현한다.
 * 로그인에 성공한 경우 인증 정보를 Spring Context Holder에 저장 후 지정된 페이지로 리다이렉트 하는 역할을 한다.
 */
@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final AdminRepository adminRepository;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LoginInfo loginInfo = (LoginInfo) authentication.getPrincipal();
        Admin admin = adminRepository.findByAdminId(loginInfo.getMemberId())
                .orElseThrow(() -> new ServiceException("관리자가 존재하지 않습니다."));

        // 관리자의 비밀번호 만료 시 로그인 실패
        if (admin.isPasswordExpired()) {
            SecurityContextHolder.clearContext();
            response.sendRedirect(UriComponentsBuilder.fromPath("/error/alert")
                    .queryParam("message", "비밀번호가 만료되었습니다. 관리자에게 문의해 주세요")
                    .queryParam("redirectUrl", "/auth/logout")
                    .toUriString());
            return;
        }

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");

        // 관리자의 비밀번호 만료 10일전 알림
        if (admin.isPasswordExpiredAlert()) {
            uriComponentsBuilder.queryParam("passwordExpiredAlert", true);
        }

        // 로그인 일시 저장
        admin.setLastLoginDate();

        // 로그인 후 페이지 이동
        response.sendRedirect(uriComponentsBuilder.toUriString());
    }
}
