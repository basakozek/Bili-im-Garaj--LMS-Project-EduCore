package org.basak.educore.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrganizationRequestDto(
        @NotBlank(message = "Organizasyon adı boş olamaz.")
        String name,

        @NotNull(message = "Brand ID boş olamaz.")
        UUID brandId
) {}

