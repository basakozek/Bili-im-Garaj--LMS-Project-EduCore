package org.basak.educore.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        @NotBlank(message = "E-posta boş olamaz.")
        String email,
        @NotBlank(message = "Şifre boş olamaz.")
        String password
) {}
