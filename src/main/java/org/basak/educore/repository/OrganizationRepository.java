package org.basak.educore.repository;
import jakarta.validation.constraints.NotBlank;
import org.basak.educore.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    boolean existsByBrandId(UUID brandId);

    boolean existsByNameIgnoreCase(String name);
}
