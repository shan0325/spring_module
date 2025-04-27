package com.sm.app.admin.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.app.admin.util.WebUtil;
import com.sm.app.common.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 서버에 요청을 할 때 접근이 가능한지 권한을 체크 후 접근할 수 없는 요청을 했을 시 동작하기 위해 AccessDeniedHandler를 상속받는 핸들러를 구현한다.
 * 오류 페이지로 이동시키거나 에러코드를 반환하는 역할을 한다.
 */
@RequiredArgsConstructor
@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    private static final String MESSAGE = "접근 권한이 없습니다.";

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        if (WebUtil.isAjaxRequest(request)) {
            handleAjaxRequest(response);
        } else {
            handlePageRedirect(response);
        }
    }

    private void handleAjaxRequest(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("exceptionResponse", ExceptionResponse.builder(MESSAGE)
                .code(String.valueOf(HttpServletResponse.SC_FORBIDDEN))
                .build());

        response.getWriter().println(objectMapper.writeValueAsString(responseMap));
    }

    private void handlePageRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(UriComponentsBuilder.fromPath("/error/error")
                .queryParam("code", HttpServletResponse.SC_FORBIDDEN)
                .queryParam("message", MESSAGE)
                .toUriString());
    }
}
