package com.sm.app.admin.config.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증이 되지 않은 사용자가 요청을 한 경우 동작하기 위해 AuthenticationEntryPoint를 상속받는 핸들러를 구현한다.
 * 오류 페이지로 이동시키거나 에러코드를 반환하는 역할을 한다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(e.getMessage(), e);

        // 인증되지 않은 경우 페이지 이동 시 사용
        response.sendRedirect("/auth/login-form");

        // 인증되지 않은 경우 에러코드 반환 시 사용
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
