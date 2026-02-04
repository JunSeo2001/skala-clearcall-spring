package com.example.clearcall.security;

import com.example.clearcall.config.JwtProperties;
import com.example.clearcall.entity.Role;
import com.example.clearcall.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey accessKey;
    private final SecretKey callKey;
    private final long accessExpMinutes;
    private final long callExpMinutes;

    public JwtService(JwtProperties properties) {
        this.accessKey = Keys.hmacShaKeyFor(properties.accessSecret().getBytes(StandardCharsets.UTF_8));
        this.callKey = Keys.hmacShaKeyFor(properties.callSecret().getBytes(StandardCharsets.UTF_8));
        this.accessExpMinutes = properties.accessExpMinutes();
        this.callExpMinutes = properties.callExpMinutes();
    }

    public String issueAccessToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpMinutes * 60);
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .signWith(accessKey, Jwts.SIG.HS256)
                .compact();
    }

    public String issueCallToken(UserPrincipal principal, UUID roomId) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(callExpMinutes * 60);
        return Jwts.builder()
                .subject(principal.email())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("userId", principal.userId())
                .claim("role", principal.role().name())
                .claim("roomId", roomId.toString())
                .claim("scope", "call")
                .signWith(callKey, Jwts.SIG.HS256)
                .compact();
    }

    public Claims validateAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(accessKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims validateCallToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(callKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String scope = claims.get("scope", String.class);
        if (!"call".equals(scope)) {
            throw new IllegalArgumentException("Invalid call token scope");
        }
        return claims;
    }

    public Role extractRole(Claims claims) {
        return Role.valueOf(claims.get("role", String.class));
    }
}
