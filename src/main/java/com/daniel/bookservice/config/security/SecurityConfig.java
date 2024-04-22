package com.daniel.bookservice.config.security;

import com.daniel.bookservice.config.jwt.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthService jwtAuthService;
    private final AuthenticationProvider authenticationProvider;
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
            "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**",
            "api/v1/user/register", "api/v1/user/login", "api/v1/admin/register"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthService, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
