package com.sm.app.admin.config.security.filter;

import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PermitAllFilter extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private static final String UNAUTHENTICATED_USER_MESSAGE = "인증되지 않은 사용자입니다.";

    private boolean isAnyRequestAuthenticatedCheck = true;
    private final List<RequestMatcher> permitAllRequestMatcher = new ArrayList<>();
    private final List<String> bypassAuthorities;

    public PermitAllFilter(boolean isAnyRequestAuthenticatedCheck, List<String> bypassAuthorities, String... permitAllPattern) {
        this.isAnyRequestAuthenticatedCheck = isAnyRequestAuthenticatedCheck;
        this.bypassAuthorities = bypassAuthorities;
        createPermitAllPattern(permitAllPattern);
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        // 인가 없이 접근 가능한 URL 체크
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (RequestMatcher requestMatcher : permitAllRequestMatcher) {
            if (requestMatcher.matches(request)) {
                return null;
            }
        }

        // 특정 권한을 가진 사용자의 요청 패스
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (bypassAuthorities.contains(authority.getAuthority())) {
                    return null;
                }
            }
        }

        // permitAllRequestMatcher를 제외한 모든 요청은 인증된 사용자만 접근 가능
        if (isAnyRequestAuthenticatedCheck) {
            if ((authentication == null) || !authentication.isAuthenticated()) {
                throw new InsufficientAuthenticationException(UNAUTHENTICATED_USER_MESSAGE);
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof String && principal.equals("anonymousUser")) {
                throw new InsufficientAuthenticationException(UNAUTHENTICATED_USER_MESSAGE);
            }
        }

        return super.beforeInvocation(object);
    }

    @Override
    public void invoke(FilterInvocation fi) throws IOException, ServletException {

        if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && super.isObserveOncePerRequest()) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }

    private void createPermitAllPattern(String... permitAllPattern) {
        for (String pattern : permitAllPattern) {
            permitAllRequestMatcher.add(new AntPathRequestMatcher(pattern));
        }
    }
}
