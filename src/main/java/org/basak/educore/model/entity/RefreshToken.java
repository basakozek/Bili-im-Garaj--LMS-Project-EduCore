package org.basak.educore.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "tbl_refresh_token")
public class RefreshToken{
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;

    private String token;

    private LocalDateTime expiryDate;
}