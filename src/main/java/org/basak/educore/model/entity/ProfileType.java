package org.basak.educore.model.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_profile_type")
public class ProfileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 0: SuperAdmin, 1: Teacher, 2: Student

    @Column(nullable = false, unique = true)
    private String name;
}