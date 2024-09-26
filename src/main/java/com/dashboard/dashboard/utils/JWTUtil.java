package com.dashboard.dashboard.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTUtil {

    private static final String SECRET = "#SECRET#";
    private static final long EXPIRATION_TIME = 86400000;  // 1 day expiration time

    // Generate JWT token with username as the subject
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }

    // Validate JWT token and extract username
    public String validateTokenAndGetUsername(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
