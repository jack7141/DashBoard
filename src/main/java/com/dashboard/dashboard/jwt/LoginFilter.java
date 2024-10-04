package com.dashboard.dashboard.jwt;

import com.dashboard.dashboard.dto.member.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = null;
        String password = null;

        // Check if the request is in JSON format
        if (request.getContentType() != null && request.getContentType().equals("application/json")) {
            try {
                // Parse the JSON request body
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> jsonMap = objectMapper.readValue(request.getInputStream(), Map.class);

                // Extract email and password from the JSON map
                email = jsonMap.get("email");
                password = jsonMap.get("password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Create an authentication token using the email and password
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        // Authenticate the token using the AuthenticationManager
        return authenticationManager.authenticate(authToken);
    }

}
