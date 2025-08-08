package org.basak.educore.model.dto.response;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String profileName,
        String organizationName,
        String classroomName // sadece öğrenci için varsa döner
) {}

