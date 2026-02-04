package com.example.clearcall.dto.call;

import jakarta.validation.constraints.NotBlank;

public record CallIntrospectRequest(@NotBlank String callToken) {
}
