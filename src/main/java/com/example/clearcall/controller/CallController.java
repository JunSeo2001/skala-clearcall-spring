package com.example.clearcall.controller;

import com.example.clearcall.dto.call.CallIntrospectRequest;
import com.example.clearcall.dto.call.CallIntrospectResponse;
import com.example.clearcall.dto.call.CallStartResponse;
import com.example.clearcall.security.UserPrincipal;
import com.example.clearcall.service.CallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calls")
@RequiredArgsConstructor
@Tag(name = "Calls", description = "통화 시작/Call Token 발급 및 검증")
public class CallController {

    private final CallService callService;

    @PostMapping("/start")
    @Operation(
            summary = "통화 시작 (Call Token 발급)",
            description = "Access JWT로 인증된 사용자를 기준으로 5분 만료 Call Token과 roomId(UUID)를 발급합니다."
    )
    public CallStartResponse start(@AuthenticationPrincipal UserPrincipal principal) {
        return callService.startCall(principal);
    }

    @PostMapping("/introspect")
    @Operation(
            summary = "Call Token 검증",
            description = "Call Token을 검증하고 valid/userId/role/roomId/expiresAt 정보를 반환합니다. FastAPI 서버에서 호출하는 용도입니다."
    )
    public CallIntrospectResponse introspect(@Valid @RequestBody CallIntrospectRequest request) {
        return callService.introspect(request.callToken());
    }
}
