package com.sm.app.domainrdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@RequiredArgsConstructor
@EnableJpaAuditing
@Configuration
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        /*return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null == authentication || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getUsername());
        };*/

        return () -> Optional.of("");
    }
}
