package com.aao.utils;

import com.aao.dto.AmenityImageRequest;
import com.aao.dto.AmenityImageResponse;
import com.aao.entity.AmenityImage;

public class AmenityImageMapper {

    public static AmenityImage toEntity(AmenityImageRequest dto) {
        AmenityImage image = new AmenityImage();
        image.setPropertyId(dto.getPropertyId());
        image.setName(dto.getName()); 
        image.setDescription(dto.getDescription());
        image.setImageName(dto.getImageName());
        image.setRelatedType(dto.getRelatedType());
        return image;
    }

    public static AmenityImageResponse toDTO(AmenityImage image) {
        AmenityImageResponse dto = new AmenityImageResponse();
        dto.setId(image.getId());
        dto.setPropertyId(image.getPropertyId());
        dto.setName(image.getName());
        dto.setDescription(image.getDescription());
        dto.setImageName(image.getImageName());
        dto.setRelatedType(image.getRelatedType());
        return dto;
    }
}