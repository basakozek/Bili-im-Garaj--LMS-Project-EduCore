package org.basak.educore.model.dto.request;

import java.util.UUID;

public record AssignCourseRequestDto(
        UUID courseId,
        UUID classroomId
) {}
