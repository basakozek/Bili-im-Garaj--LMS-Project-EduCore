package org.basak.educore.model.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BrandResponseDto (
        UUID id,
        String name,
        String code)
{}
