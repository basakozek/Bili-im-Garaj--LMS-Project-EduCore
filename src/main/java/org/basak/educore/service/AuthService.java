package org.basak.educore.service;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.model.entity.RefreshToken;
import org.springframework.stereotype.Service;
import org.basak.educore.model.dto.response.LoginResponseDto;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.model.entity.User;
import org.basak.educore.util.JwtManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtManager jwtManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDto login(String email, String password) {
        User user = userService.findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new EduCoreException(ErrorType.INVALID_PASSWORD);
        }

        if (user.getProfileType() == null) {
            throw new EduCoreException(ErrorType.ROLE_NOT_ASSIGNED);
        }

        // UUID userId + role string ile token üretiliyor
        String accessToken = jwtManager.generateToken(user);

        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return new LoginResponseDto(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        RefreshToken tokenEntity = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new EduCoreException(ErrorType.INVALID_REFRESH_TOKEN));

        if (refreshTokenService.isExpired(tokenEntity)) {
            refreshTokenService.delete(tokenEntity);
            throw new EduCoreException(ErrorType.EXPIRED_REFRESH_TOKEN);
        }

        User user = userService.findById(tokenEntity.getUserId());

        return jwtManager.generateToken(user);
    }

    public String forgotPassword(String email) {
        User user = userService.findByEmail(email);

        if (user.getPasswordResetCode() != null) {
            throw new EduCoreException(ErrorType.RESET_CODE_ALREADY_SENT);
        }

        // Kod üretme
        String resetCode = String.valueOf((int)(Math.random() * 900_000) + 100_000);
        user.setPasswordResetCode(resetCode);
        userService.save(user);

        // Güvenli olan kodun mail ile gönderilmesi
        // ama projeyi basit tutmak amacıyla response olarak dönüldü
        return resetCode;
    }

    public void resetPassword(String email, String resetCode, String newPassword, String rePassword) {
        if (!newPassword.equals(rePassword)) {
            throw new EduCoreException(ErrorType.PASSWORD_MISMATCH_ERROR);
        }
        User user = userService.findByEmail(email);

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new EduCoreException(ErrorType.PASSWORD_SAME_AS_OLD);
        }

        if (!resetCode.equals(user.getPasswordResetCode())) {
            throw new EduCoreException(ErrorType.INVALID_RESET_CODE);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetCode(null);
        userService.save(user);
    }
}