package org.basak.educore.model.dto.response;

import java.util.UUID;

public record TeacherCourseResponseDto(
        UUID id,
        String name,
        String imageUrl
) {}
