package com.aao.utils;

import org.springframework.stereotype.Component;

import com.aao.dto.PropertyAmenityRequestDto;
import com.aao.dto.PropertyAmenityResponseDto;
import com.aao.entity.Property;
import com.aao.entity.PropertyAmenity;

@Component
public class PropertyAmenityMapper {
 
	

	    public PropertyAmenity toEntity(
	            PropertyAmenityRequestDto dto,
	            Property property
	    ) {
	        PropertyAmenity amenity = new PropertyAmenity();
	        amenity.setName(dto.getName());
	        amenity.setAvailable(dto.isAvailable());
	        amenity.setNotes(dto.getNotes());
	        amenity.setProperty(property);
	        return amenity;
	    }

	    public PropertyAmenityResponseDto toDto(PropertyAmenity amenity) {
	        PropertyAmenityResponseDto dto = new PropertyAmenityResponseDto();
	        dto.setId(amenity.getPropertyAmenityId());
	        dto.setName(amenity.getName());
	        dto.setAvailabel(amenity.isAvailable());
	        dto.setNotes(amenity.getNotes());
	        return dto;
	    }
	

}