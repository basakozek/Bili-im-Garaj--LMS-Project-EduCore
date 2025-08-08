package org.basak.educore.repository;
import org.basak.educore.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);
}