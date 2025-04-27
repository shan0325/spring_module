package com.sm.app.admin.config.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Form Login에 실패할 경우 동작하기 위해 AuthenticationFailureHandler를 상속받는 핸들러를 구현한다.
 * 로그인 실패 시 로직 실행 후 로그인 페이지로 리다이렉트하는 역할을 한다.
 */
@Slf4j
@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(e.getMessage(), e);

        // 로그인 실패 처리
        response.sendRedirect(UriComponentsBuilder.fromPath("/auth/login-form")
                .queryParam("message", e.getMessage())
                .toUriString());
    }
}
