package com.aao.serviceInterface;

import com.aao.dto.PropertyDto;
import com.aao.entity.PropertyStatus;
import com.aao.response.ApiResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PropertyService {
    ApiResponse<PropertyDto> addProperty(PropertyDto propertyDto);

    ApiResponse<List<PropertyDto>> getAllProperties();

    ApiResponse<PropertyDto> getPropertyById(Long propertyId);

    ApiResponse<PropertyDto> updateProperty(Long propertyId, PropertyDto propertyDto);

    ApiResponse<Void> deleteProperty(Long propertyId);
    
    ApiResponse<List<PropertyDto>>serachProperty(String  city,String state, String categoryType,String propertyType,BigDecimal minPrice,BigDecimal maxPrice);
    
     ApiResponse<PropertyDto> updatePropertyStaus(Long PropertyId, PropertyStatus newStaus);
}
