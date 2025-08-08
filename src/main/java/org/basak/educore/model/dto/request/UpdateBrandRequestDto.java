package org.basak.educore.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateBrandRequestDto(
        @NotBlank(message = "Marka adı boş olamaz.")
        String name,
        @NotBlank(message = "Kod boş olamaz.")
        String code
) {
}
