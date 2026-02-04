package com.example.clearcall.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ApiError {
    private OffsetDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private String path;
}
