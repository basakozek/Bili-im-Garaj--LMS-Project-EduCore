package org.basak.educore.model.dto.request;

import java.util.UUID;

public record CreateUserRequestDto(
        String email,
        String password,
        String firstName,
        String lastName,
        UUID organizationId,
        Integer profileId,
        UUID classroomId // Student için zorunlu, diğerleri için null olabilir
) {}
