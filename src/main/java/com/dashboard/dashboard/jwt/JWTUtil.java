package com.dashboard.dashboard.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JWTUtil {

    private Key key;
    private final long expirationTimeMs;  // 1 day expiration time

    public JWTUtil(@Value("${spring.jwt.secret}")String secret, @Value("${spring.jwt.expiration}") long expirationTimeMs) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
        this.key = Keys.hmacShaKeyFor(byteSecretKey);
        this.expirationTimeMs = expirationTimeMs;
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String email, String role) {

        Claims claims = Jwts.claims();
        claims.put("username", email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the token (ensure it is correctly signed and not expired)
    public boolean validateToken(String token) {
        try {
            getClaims(token);  // Just try to parse the claims; if it fails, the token is invalid
            return true;
        } catch (Exception e) {
            return false;  // If any exception occurs, the token is considered invalid
        }
    }

    // Helper method to get claims from a token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
