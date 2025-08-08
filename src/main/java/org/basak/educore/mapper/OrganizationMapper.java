package org.basak.educore.mapper;
import org.basak.educore.model.dto.response.OrganizationResponseDto;
import org.basak.educore.model.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationResponseDto toResponseDto(Organization organization) {
        return new OrganizationResponseDto(
                organization.getId(),
                organization.getName(),
                organization.getBrand() != null ? organization.getBrand().getId() : null
        );
    }
}