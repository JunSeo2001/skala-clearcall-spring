package com.example.clearcall.dto.user;

import com.example.clearcall.entity.Role;

public record MeResponse(Long id, String email, Role role) {
}
