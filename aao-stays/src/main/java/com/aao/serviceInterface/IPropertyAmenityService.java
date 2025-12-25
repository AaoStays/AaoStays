package com.aao.serviceInterface;

import com.aao.dto.PropertyAmenityDTO;
import com.aao.entity.PropertyAmenity;
import com.aao.response.ApiResponse;

import java.util.List;

public interface IPropertyAmenityService {

    PropertyAmenityDTO addAmenityToProperty(PropertyAmenityDTO dto);

    PropertyAmenityDTO updateAmenityStatus(Long id, Boolean isAvailable);

    void deletePropertyAmenity(Long id);

    List<PropertyAmenityDTO> getAmenitiesByProperty(Long propertyId);

    
    ApiResponse<PropertyAmenityDTO> addAmenityToProperty( PropertyAmenity amenity);
    PropertyAmenityDTO getById(Long id);
}
