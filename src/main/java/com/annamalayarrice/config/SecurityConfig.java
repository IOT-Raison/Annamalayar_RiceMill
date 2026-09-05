package com.annamalayarrice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * TEMPORARY security config.
 *
 * spring-boot-starter-security is on the classpath (added for future JWT auth), which makes
 * Spring Boot auto-lock every endpoint behind a login form by default - that's why hitting
 * any /api/... URL was redirecting to /login.
 *
 * For now, while we're just building out the dashboard/report endpoints, this permits all
 * requests. Once real authentication (JWT) is wired in, replace this with a config that:
 *   - requires a valid token for /api/** (except maybe /api/health)
 *   - keeps CSRF disabled (fine for a stateless REST API, not relevant once using JWT)
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());

        return http.build();
    }
}
