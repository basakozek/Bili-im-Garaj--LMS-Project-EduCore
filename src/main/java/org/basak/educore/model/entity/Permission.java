package org.basak.educore.model.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_permission")
public class Permission {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ProfileType role;

    @Column(name = "resource_name")
    private String resourceName;

    private boolean canCreate;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;

}