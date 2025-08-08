package org.basak.educore.model.dto.response;
import java.util.UUID;

public record TeacherClassroomAssignmentResponseDto(
        UUID id,
        UUID teacherId,
        String teacherFullName,
        UUID classroomId,
        String classroomName
) {
}
