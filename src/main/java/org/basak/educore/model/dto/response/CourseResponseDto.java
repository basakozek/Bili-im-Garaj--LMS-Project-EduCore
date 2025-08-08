package org.basak.educore.model.dto.response;

import java.util.UUID;

public record CourseResponseDto(
        UUID id,
        String name,
        String imageUrl
) {}

