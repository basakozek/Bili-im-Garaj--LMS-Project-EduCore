package org.basak.educore.model.dto.request;

import java.util.UUID;

public record AssignTeacherRequestDto(
        UUID teacherId,
        UUID classroomId
) {}
