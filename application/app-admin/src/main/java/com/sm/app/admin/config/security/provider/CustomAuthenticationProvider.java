package com.sm.app.admin.config.security.provider;

import com.sm.app.admin.config.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = userDetailsService.loadUserByUsername(username);
        validateUser(user, username);
        validatePassword(password, user.getPassword());

        // principal(접근대상 정보), credential(비밀번호), authorities(권한 목록)를 token에 담아 반환
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    private void validateUser(UserDetails user, String username) {
        if (user == null) {
            throw new UsernameNotFoundException("찾을 수 없는 회원 정보입니다. [" + username + "]");
        }
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("비밀번호가 잘못되었습니다.");
        }
    }

    /**
     * 이 AuthenticationProvider가 특정 Authentication 타입을 지원하는지 여부를 반환한다.
     * 여기서는 UsernamePasswordAuthenticationToken 클래스를 지원한다.
     * @param authentication
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
