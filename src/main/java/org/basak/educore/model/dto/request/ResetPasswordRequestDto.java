package org.basak.educore.model.dto.request;

public record ResetPasswordRequestDto(
        String email,
        String resetCode,
        String newPassword,
        String rePassword
) {}
