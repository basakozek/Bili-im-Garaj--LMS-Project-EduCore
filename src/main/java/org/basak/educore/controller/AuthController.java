package org.basak.educore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.basak.educore.model.dto.request.LoginRequestDto;
import org.basak.educore.model.dto.request.ResetPasswordRequestDto;
import org.basak.educore.model.dto.response.LoginResponseDto;
import org.basak.educore.model.dto.response.BaseResponse;
import org.basak.educore.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto tokens = authService.login(dto.email(), dto.password());
        return ResponseEntity.ok(BaseResponse.<LoginResponseDto>builder()
                .code(200)
                .success(true)
                .message("Giriş başarılı.")
                .data(tokens)
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<String>> refreshAccessToken(@RequestParam String refreshToken) {
        String newAccessToken = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .data(newAccessToken)
                .message("Yeni access token üretildi.")
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<String>> forgotPassword(@RequestParam String email) {
        String resetCode = authService.forgotPassword(email);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .message("Şifrenizi onaylamak için reset-password alanına gidiniz.")
                .data("Şifre sıfırlama kodu: "+resetCode)
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse<Boolean>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
        authService.resetPassword(dto.email(), dto.resetCode(), dto.newPassword(), dto.rePassword());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .data(true)
                .message("Şifre başarıyla güncellendi.")
                .build());
    }
}