package com.aao.serviceInterface;

import com.aao.dto.PropertyAmenityRequestDto;
import com.aao.dto.PropertyAmenityResponseDto;
import com.aao.response.ApiResponse;

public interface PropertyAmenityService {

	
	ApiResponse<PropertyAmenityResponseDto> addAmenity(
	        PropertyAmenityRequestDto amenityRequestDto
	);
	
	
	ApiResponse<PropertyAmenityResponseDto> getAmenityById(Long propertyAmenityId);
	
	ApiResponse<Void> deletePropertyAmenity(Long id);
	
	
} 
