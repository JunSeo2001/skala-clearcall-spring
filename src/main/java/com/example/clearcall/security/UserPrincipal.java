package com.example.clearcall.security;

import com.example.clearcall.entity.Role;

public record UserPrincipal(Long userId, String email, Role role) {
}
