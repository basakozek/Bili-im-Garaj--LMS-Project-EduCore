package org.basak.educore.model.dto.response;

import java.util.UUID;

public record TeacherStudentResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email
) {}