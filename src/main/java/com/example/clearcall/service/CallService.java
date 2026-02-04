package com.example.clearcall.service;

import com.example.clearcall.dto.call.CallIntrospectResponse;
import com.example.clearcall.dto.call.CallStartResponse;
import com.example.clearcall.entity.Role;
import com.example.clearcall.security.JwtService;
import com.example.clearcall.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CallService {

    private final JwtService jwtService;

    public CallStartResponse startCall(UserPrincipal principal) {
        UUID roomId = UUID.randomUUID();
        String callToken = jwtService.issueCallToken(principal, roomId);
        return new CallStartResponse(callToken, roomId);
    }

    public CallIntrospectResponse introspect(String callToken) {
        try {
            Claims claims = jwtService.validateCallToken(callToken);
            Long userId = claims.get("userId", Long.class);
            Role role = Role.valueOf(claims.get("role", String.class));
            UUID roomId = UUID.fromString(claims.get("roomId", String.class));
            Instant expiresAt = claims.getExpiration().toInstant();
            return new CallIntrospectResponse(true, userId, role, roomId, expiresAt);
        } catch (Exception ex) {
            return new CallIntrospectResponse(false, null, null, null, null);
        }
    }
}
