package com.example.clearcall.controller;

import com.example.clearcall.dto.auth.AuthResponse;
import com.example.clearcall.dto.auth.LoginRequest;
import com.example.clearcall.dto.auth.RegisterRequest;
import com.example.clearcall.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원가입/로그인 및 Access JWT 발급")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "회원가입",
            description = "이메일/비밀번호/권한(role)을 받아 계정을 생성하고 Access JWT를 반환합니다."
    )
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "이메일/비밀번호로 인증 후 Access JWT를 발급합니다."
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
