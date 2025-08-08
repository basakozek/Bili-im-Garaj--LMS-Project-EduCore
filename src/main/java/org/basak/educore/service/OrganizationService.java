package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.OrganizationMapper;
import org.basak.educore.model.dto.request.CreateOrganizationRequestDto;
import org.basak.educore.model.dto.response.OrganizationResponseDto;
import org.basak.educore.model.entity.Brand;
import org.basak.educore.model.entity.Organization;
import org.basak.educore.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final BrandService brandService;

    public boolean existsByBrandId(UUID brandId) {
        return organizationRepository.existsByBrandId(brandId);
    }
    @Transactional
    public OrganizationResponseDto createOrganization(CreateOrganizationRequestDto dto) {
        // Aynı isimle daha önce kayıtlı mı kontrol et
        if (organizationRepository.existsByNameIgnoreCase(dto.name())) {
            throw new EduCoreException(ErrorType.ORGANIZATION_ALREADY_EXISTS);
        }

        Brand brand = brandService.findById(dto.brandId());

        Organization organization = Organization.builder()
                .name(dto.name())
                .brand(brand)
                .build();

        organization = organizationRepository.save(organization);
        return organizationMapper.toResponseDto(organization);
    }

    public List<OrganizationResponseDto> findAllOrganization() {
        List<Organization> list = organizationRepository.findAll();

        if (list.isEmpty()) {
            throw new EduCoreException(ErrorType.ORGANIZATION_NOT_FOUND);
        }

        return list.stream()
                .map(organizationMapper::toResponseDto)
                .toList();
    }

    public Organization findById(UUID id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.ORGANIZATION_NOT_FOUND));
    }


}
