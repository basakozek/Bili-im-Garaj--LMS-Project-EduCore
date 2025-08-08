package org.basak.educore.model.dto.request;

import java.util.UUID;

public record CreateClassroomRequestDto(
        String name,
        UUID organizationId
) {}
