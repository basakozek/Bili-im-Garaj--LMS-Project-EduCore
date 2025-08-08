package org.basak.educore.service;

import lombok.RequiredArgsConstructor;
import org.basak.educore.model.entity.RefreshToken;
import org.basak.educore.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken createRefreshToken(UUID userId) {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();
        return repository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public void delete(RefreshToken refreshToken) {
        repository.delete(refreshToken);
    }
}