package com.aao.utils;


import com.aao.dto.AmenityImageRequest;
import com.aao.dto.AmenityImageResponse;
import com.aao.entity.AmenityImage;
import com.aao.entity.Property;

public class AmenityImageMapper {

    public static AmenityImage toEntity(AmenityImageRequest dto) {
        AmenityImage image = new AmenityImage();
        
        Property property = new Property();
        property.setPropertyId(dto.getPropertyId());
        image.setProperty(property);

        image.setName(dto.getName()); 
        image.setDescription(dto.getDescription());
        image.setImageName(dto.getImageName());
        image.setRelatedType(dto.getRelatedType());
        return image;
    }

    public static AmenityImageResponse toDTO(AmenityImage image) {
        AmenityImageResponse dto = new AmenityImageResponse();
        dto.setId(image.getId());
        dto.setpropertyId(image.getProperty() != null ? image.getProperty().getPropertyId() : null);
        dto.setname(image.getName());
        dto.setdescription(image.getDescription());
        dto.setimageName(image.getImageName());
        dto.setRelatedType(image.getRelatedType());
        return dto;
    }
}