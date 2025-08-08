package org.basak.educore.service;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.BrandMapper;
import org.basak.educore.model.dto.request.CreateBrandRequestDto;
import org.basak.educore.model.dto.request.UpdateBrandRequestDto;
import org.basak.educore.model.dto.response.BrandResponseDto;
import org.basak.educore.model.entity.Brand;
import org.basak.educore.repository.BrandRepository;
import org.basak.educore.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final OrganizationRepository organizationRepository;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandResponseDto createBrand(CreateBrandRequestDto dto) {
        if (brandRepository.existsByNameIgnoreCase(dto.name())) {
            throw new EduCoreException(ErrorType.BRAND_ALREADY_EXISTS);
        }

        if (brandRepository.existsByCodeIgnoreCase(dto.code())) {
            throw new EduCoreException(ErrorType.BRAND_CODE_ALREADY_EXISTS);
        }

        Brand brand = Brand.builder()
                .name(dto.name())
                .code(dto.code())
                .build();

        brand = brandRepository.save(brand);
        return brandMapper.toResponseDto(brand);
    }

    public List<BrandResponseDto> findAllBrand() {
        List<Brand> brands = brandRepository.findAll();

        if (brands.isEmpty()) {
            throw new EduCoreException(ErrorType.BRAND_NOT_FOUND);
        }

        return brands.stream()
                .map(brandMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public BrandResponseDto updateBrand(UUID id, UpdateBrandRequestDto dto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.BRAND_NOT_FOUND));

        // Eğer isim değiştiriliyorsa ve başka bir brand tarafından kullanılıyorsa
        if (!brand.getName().equalsIgnoreCase(dto.name()) &&
                brandRepository.existsByNameIgnoreCase(dto.name())) {
            throw new EduCoreException(ErrorType.BRAND_ALREADY_EXISTS);
        }

        // Eğer kod değiştiriliyorsa ve başka bir brand tarafından kullanılıyorsa
        if (!brand.getCode().equalsIgnoreCase(dto.code()) &&
                brandRepository.existsByCodeIgnoreCase(dto.code())) {
            throw new EduCoreException(ErrorType.BRAND_CODE_ALREADY_EXISTS);
        }

        brand.setName(dto.name());
        brand.setCode(dto.code());

        return brandMapper.toResponseDto(brandRepository.save(brand));
    }

    @Transactional
    public void deleteBrand(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.BRAND_NOT_FOUND));

        if (organizationRepository.existsByBrandId(brand.getId())) {
            throw new EduCoreException(ErrorType.BRAND_HAS_ORGANIZATIONS);
        }

        brandRepository.delete(brand);
    }

    public Brand findById(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.BRAND_NOT_FOUND));
    }

}