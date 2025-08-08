package org.basak.educore.model.dto.response;

import java.util.UUID;

public record OrganizationResponseDto(
        UUID id,
        String name,
        UUID brandId
) {}

