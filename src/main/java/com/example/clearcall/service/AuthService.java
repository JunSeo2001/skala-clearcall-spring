package com.example.clearcall.service;

import com.example.clearcall.dto.auth.AuthResponse;
import com.example.clearcall.dto.auth.LoginRequest;
import com.example.clearcall.dto.auth.RegisterRequest;
import com.example.clearcall.entity.User;
import com.example.clearcall.exception.ApiException;
import com.example.clearcall.repository.UserRepository;
import com.example.clearcall.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "AUTH_DUPLICATE", "Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        User saved = userRepository.save(user);

        String token = jwtService.issueAccessToken(saved);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "AUTH_INVALID", "Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "AUTH_INVALID", "Invalid credentials");
        }

        String token = jwtService.issueAccessToken(user);
        return new AuthResponse(token);
    }
}
