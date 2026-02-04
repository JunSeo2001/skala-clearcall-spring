package com.example.clearcall.controller;

import com.example.clearcall.dto.user.MeResponse;
import com.example.clearcall.entity.User;
import com.example.clearcall.exception.NotFoundException;
import com.example.clearcall.repository.UserRepository;
import com.example.clearcall.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User", description = "현재 로그인 사용자 정보")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "Access JWT로 인증된 사용자의 기본 정보(id, email, role)를 반환합니다."
    )
    public MeResponse me(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            throw new NotFoundException("User not found");
        }
        User user = userRepository.findById(principal.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        return new MeResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
