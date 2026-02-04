package com.example.clearcall.controller;

import com.example.clearcall.dto.report.ReportRequest;
import com.example.clearcall.dto.report.ReportResponse;
import com.example.clearcall.security.UserPrincipal;
import com.example.clearcall.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "상담 리포트 CRUD (AGENT/ADMIN 전용)")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(
            summary = "리포트 목록 조회",
            description = "모든 리포트를 조회합니다. AGENT 또는 ADMIN 권한이 필요합니다."
    )
    public List<ReportResponse> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "리포트 단건 조회",
            description = "리포트 ID로 상세 정보를 조회합니다. AGENT 또는 ADMIN 권한이 필요합니다."
    )
    public ReportResponse getById(@Parameter(description = "리포트 ID") @PathVariable Long id) {
        return reportService.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "리포트 생성",
            description = "title/content로 리포트를 생성합니다. 생성자는 Access JWT의 userId로 저장됩니다."
    )
    public ReportResponse create(@Valid @RequestBody ReportRequest request,
                                 @AuthenticationPrincipal UserPrincipal principal) {
        return reportService.create(request, principal.userId());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "리포트 수정",
            description = "리포트 ID 기준으로 title/content를 수정합니다. AGENT 또는 ADMIN 권한이 필요합니다."
    )
    public ReportResponse update(@Parameter(description = "리포트 ID") @PathVariable Long id,
                                 @Valid @RequestBody ReportRequest request) {
        return reportService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "리포트 삭제",
            description = "리포트 ID 기준으로 삭제합니다. AGENT 또는 ADMIN 권한이 필요합니다."
    )
    public void delete(@Parameter(description = "리포트 ID") @PathVariable Long id) {
        reportService.delete(id);
    }
}
