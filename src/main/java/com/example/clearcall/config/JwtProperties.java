package com.example.clearcall.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        @NotBlank String accessSecret,
        @NotBlank String callSecret,
        @Min(1) long accessExpMinutes,
        @Min(1) long callExpMinutes
) {
}
