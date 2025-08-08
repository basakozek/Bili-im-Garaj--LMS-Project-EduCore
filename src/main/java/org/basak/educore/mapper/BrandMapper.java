package org.basak.educore.mapper;

import org.basak.educore.model.dto.response.BrandResponseDto;
import org.basak.educore.model.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandResponseDto toResponseDto(Brand brand) {
        return BrandResponseDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .code(brand.getCode())
                .build();
    }
}

