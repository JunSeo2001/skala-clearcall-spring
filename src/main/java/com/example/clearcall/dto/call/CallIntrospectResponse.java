package com.example.clearcall.dto.call;

import com.example.clearcall.entity.Role;

import java.time.Instant;
import java.util.UUID;

public record CallIntrospectResponse(
        boolean valid,
        Long userId,
        Role role,
        UUID roomId,
        Instant expiresAt
) {
}
