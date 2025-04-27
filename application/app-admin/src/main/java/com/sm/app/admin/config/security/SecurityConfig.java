package com.sm.app.admin.config.security;

import com.sm.app.admin.config.security.factory.UrlResourcesMapFactoryBean;
import com.sm.app.admin.config.security.filter.PermitAllFilter;
import com.sm.app.admin.config.security.handler.CustomLoginFailHandler;
import com.sm.app.admin.config.security.handler.CustomLoginSuccessHandler;
import com.sm.app.admin.config.security.handler.WebAccessDeniedHandler;
import com.sm.app.admin.config.security.handler.WebAuthenticationEntryPoint;
import com.sm.app.admin.config.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.sm.app.admin.config.security.provider.CustomAuthenticationProvider;
import com.sm.app.admin.config.security.service.CustomUserDetailsService;
import com.sm.app.admin.config.security.service.RoleHierarchyService;
import com.sm.app.admin.config.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ROLE_PREFIX = "ROLE_";

    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final WebAuthenticationEntryPoint webAuthenticationEntryPoint;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailHandler customLoginFailHandler;
    private final CustomUserDetailsService userDetailsService;
    private final SecurityResourceService securityResourceService;
    private final RoleHierarchyService roleHierarchyService;

    // permitAll 필터 사용을 위한 자원 설정(인가 없이 접근 가능한 URL)
    private final String[] permitAllResources = {
            "/h2-console/**",
            "/css/**",
            "/js/**",
            "/modules/**",
            "/img/**",
            "/favicon.ico",
            "/error/**",
            "/auth/**"
    };

    // permitAll 필터 사용을 위한 특정 권한 설정(인가 없이 접근 가능한 권한)
    private final List<String> bypassAuthorities = List.of(
            ROLE_PREFIX + "ADMIN"
    );

    /**
     * 이 메서드는 정적 자원에 대해 보안을 적용하지 않도록 설정한다.
     * 정적 자원은 보통 HTML, CSS, JavaScript, 이미지 파일 등을 의미하며, 이들에 대해 보안을 적용하지 않음으로써 성능을 향상시킬 수 있다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers().frameOptions().disable() // h2 사용시
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(webAccessDeniedHandler) // 권한이 없는 사용자 접근 시
                        .authenticationEntryPoint(webAuthenticationEntryPoint) // 인증되지 않은 사용자 접근 시
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login-form") // 로그인 페이지 URL을 설정
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("memberId")
                        .successHandler(customLoginSuccessHandler) // 로그인 성공 시
                        .failureHandler(customLoginFailHandler) // 로그인 실패 시
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/auth/login-form") // 로그아웃 성공 후 이동할 URL 설정
                        .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
                        .deleteCookies("JSESSIONID") // 로그아웃 후 쿠기 삭제 설정
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // 최대 허용 가능 세션 수
                        .maxSessionsPreventsLogin(false) // true 시 두번째 인증 로그인 불가, false 시 기존 접속된 사용자 session 종료, 기본이 false
                        .expiredUrl("/error/alert?message="
                                + URLEncoder.encode("세션이 만료되었습니다.\n같은 사용자로 여러 개의 동시 로그인을 시도했기 때문일 수 있습니다.", StandardCharsets.UTF_8)
                                + "&redirectUrl=/auth/login-form") // 세션 만료 시 이동 할 주소
                )
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class) // 인가 처리
                .build();
    }

    // 실제 인증을 담당하는 provider
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(passwordEncoder, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
    }

    // 비밀번호 암호화를 위한 Encoder 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인가 처리를 위한 FilterSecurityInterceptor 설정
    @Bean
    public PermitAllFilter customFilterSecurityInterceptor() {
        PermitAllFilter permitAllFilter = new PermitAllFilter(true, bypassAuthorities, permitAllResources);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affirmativeBased());
        permitAllFilter.setAuthenticationManager(authenticationManager(customAuthenticationProvider(passwordEncoder())));
        return permitAllFilter;
    }

    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        return urlResourcesMapFactoryBean;
    }

    // AffirmativeBased클래스는 등록된 Voter클래스 객체 중 단 하나라도 허가되면 최종적으로 접근 허가 처리
    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        //accessDecisionVoters.add(new RoleVoter()); // 기본적인 RoleVoter를 사용할 경우
        accessDecisionVoters.add(roleHierarchyVoter()); // RoleHierarchy를 사용할 경우
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleHierarchyVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(roleHierarchyService.getAllRoleHierarchy());
        return roleHierarchy;
    }
}
