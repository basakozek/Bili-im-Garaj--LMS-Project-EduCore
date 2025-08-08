package org.basak.educore.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.basak.educore.util.JwtManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EduCoreSecurityConfig {

    private final JwtManager jwtManager;
    private final JwtUserDetails jwtUserDetails;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtManager, jwtUserDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("SecurityFilterChain initialized.");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        // Swagger & Auth Public
                        .requestMatchers(
                                "/swagger-ui/",
                                "/v3/api-docs/",
                                "/swagger-ui.html",
                                "/swagger-resources/",
                                "/webjars/",
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh-token",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password"
                        ).permitAll()

                        // SuperAdmin endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/brands").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/brands").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/brands/").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/brands/").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/organizations").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/organizations").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/users").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/users").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/classrooms").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/courses").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/courses/assign").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/teachers/assign-classroom").hasAuthority("ROLE_SUPERADMIN")

                        // Teacher endpoints
                        .requestMatchers("/api/v1/teachers/my-classes",
                                "/api/v1/teachers/my-students",
                                "/api/v1/teachers/my-courses").hasAuthority("ROLE_TEACHER")

                        // Student endpoints
                        .requestMatchers("/api/v1/students/my-courses").hasAuthority("ROLE_STUDENT")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
