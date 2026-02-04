package com.example.clearcall.dto.report;

import java.time.OffsetDateTime;

public record ReportResponse(
        Long id,
        String title,
        String content,
        OffsetDateTime createdAt,
        Long createdByUserId
) {
}
