package org.basak.educore.model.dto.response;

import java.util.UUID;

public record CourseAssignmentResponseDto(
        UUID id,
        UUID courseId,
        String courseName,
        UUID classroomId,
        String classroomName
) {
}
