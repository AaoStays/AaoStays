package com.aao.service;

import java.util.List;

import com.aao.dto.PropertyAmenityDTO;

public interface IPropertyAmenityService {

	List<PropertyAmenityDTO> getAmenitiesByProperty(Long propertyId);

	PropertyAmenityDTO getById(Long id);

	void deletePropertyAmenity(Long id);

}
