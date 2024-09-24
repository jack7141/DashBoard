package com.dashboard.dashboard;

import com.dashboard.dashboard.domain.member.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF for simplicity (add appropriate handling if required in production)
                .csrf(csrf -> csrf.disable())

                // 2. Disable X-Frame-Options header (e.g., for H2 console access)
                .headers(headers -> headers.frameOptions().disable())

                // 3. Define URL authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Allow access to Swagger and API docs without authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/swagger-config", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/swagger-config").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/logistics").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                        // Publicly accessible paths
                        .requestMatchers("/", "/login/**").permitAll()

                        // Protected resources with role-based access
                        .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
                        .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // 4. Handle 401 and 403 errors with custom JSON responses
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }

    // Custom handler for 401 Unauthorized errors
    private final AuthenticationEntryPoint unauthorizedEntryPoint = (request, response, authException) -> {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized access. Please log in.");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = new ObjectMapper().writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    };

    // Custom handler for 403 Forbidden errors
    private final AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Access denied. You do not have permission to access this resource.");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = new ObjectMapper().writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    };

    // Custom error response class
    @Getter
    @RequiredArgsConstructor
    public static class ErrorResponse {
        private final HttpStatus status;
        private final String message;
    }
}
