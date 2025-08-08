package org.basak.educore.model.dto.response;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {}

