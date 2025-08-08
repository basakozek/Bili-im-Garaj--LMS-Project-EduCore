package org.basak.educore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.basak.educore.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtManager {

    @Value("${educore.jwt.secret-key}")
    private String secretKey;

    @Value("${educore.jwt.issuer}")
    private String issuer;

    private Algorithm algorithm;
    private final long expTime = 3600L; // 1 saatlik token

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(secretKey);
    }

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(expTime))
                .withClaim("userId", user.getId().toString())
                .withClaim("profileId", user.getProfileType().getId())
                .withClaim("email", user.getEmail())
                .sign(algorithm);
    }

    public Optional<UUID> validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String userIdStr = decodedJWT.getClaim("userId").asString();
            return Optional.of(UUID.fromString(userIdStr));
        } catch (JWTVerificationException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public UUID getUserIdFromToken(String token) {
        return validateToken(token).orElseThrow(() -> new RuntimeException("Invalid JWT Token"));
    }

    public Integer getProfileId(String token) {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("profileId").asInt();
    }
}