package org.basak.educore.model.dto.response;

import java.util.UUID;

public record ClassroomResponseDto(
        UUID id,
        String name,
        String organizationName
) {}
