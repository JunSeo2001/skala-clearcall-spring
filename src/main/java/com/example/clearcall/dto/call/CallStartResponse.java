package com.example.clearcall.dto.call;

import java.util.UUID;

public record CallStartResponse(String callToken, UUID roomId) {
}
