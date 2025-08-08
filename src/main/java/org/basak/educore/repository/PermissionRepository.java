package org.basak.educore.repository;
import org.basak.educore.model.entity.Permission;
import org.basak.educore.model.entity.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByRoleAndResourceName(ProfileType role, String resourceName);
}