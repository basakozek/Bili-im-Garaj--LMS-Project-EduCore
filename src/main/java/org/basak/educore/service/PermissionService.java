package org.basak.educore.service;
import lombok.RequiredArgsConstructor;
import org.basak.educore.model.entity.ProfileType;
import org.basak.educore.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public boolean hasPermission(ProfileType role, String resourceName, String action) {
        return permissionRepository.findByRoleAndResourceName(role, resourceName)
                .map(permission -> switch (action.toLowerCase()) {
                    case "create" -> permission.isCanCreate();
                    case "read" -> permission.isCanRead();
                    case "update" -> permission.isCanUpdate();
                    case "delete" -> permission.isCanDelete();
                    default -> false;
                })
                .orElse(false);
    }
}