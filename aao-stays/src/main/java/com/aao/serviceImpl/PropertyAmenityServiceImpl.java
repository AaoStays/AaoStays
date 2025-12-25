package com.aao.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aao.dto.PropertyAmenityDTO;
import com.aao.entity.PropertyAmenity;
import com.aao.repo.PropertyAmenityRepository;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.IPropertyAmenityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyAmenityServiceImpl implements IPropertyAmenityService {

    private final PropertyAmenityRepository propertyAmenityRepository;

    // =========================
    // ADD PROPERTY AMENITY
    // =========================
    @Override
    public PropertyAmenityDTO addAmenityToProperty(PropertyAmenityDTO dto) {

        boolean exists = propertyAmenityRepository
                .existsByPropertyIdAndAmenityId(dto.getPropertyId(), dto.getAmenityId());

        if (exists) {
            throw new RuntimeException("Amenity already assigned to this property");
        }

        PropertyAmenity entity = new PropertyAmenity();
        entity.setPropertyId(dto.getPropertyId());
        entity.setAmenityId(dto.getAmenityId());
        entity.setIsAvailable(
                dto.getIsAvailable() != null ? dto.getIsAvailable() : true
        );
        entity.setNotes(dto.getNotes());

        PropertyAmenity savedEntity = propertyAmenityRepository.save(entity);

        return mapToDTO(savedEntity);
    }

    // =========================
    // UPDATE AVAILABILITY
    // =========================
    @Override
    public PropertyAmenityDTO updateAmenityStatus(Long id, Boolean isAvailable) {

        PropertyAmenity entity = propertyAmenityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Property Amenity not found with id: " + id));

        entity.setIsAvailable(isAvailable);

        PropertyAmenity updated = propertyAmenityRepository.save(entity);
        return mapToDTO(updated);
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void deletePropertyAmenity(Long id) {

        if (!propertyAmenityRepository.existsById(id)) {
            throw new RuntimeException("Property Amenity not found with id: " + id);
        }

        propertyAmenityRepository.deleteById(id);
    }

    // =========================
    // GET BY PROPERTY ID
    // =========================
    @Override
    public List<PropertyAmenityDTO> getAmenitiesByProperty(Long propertyId) {

        return propertyAmenityRepository.findByPropertyId(propertyId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public PropertyAmenityDTO getById(Long id) {

        PropertyAmenity entity = propertyAmenityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Property Amenity not found with id: " + id));

        return mapToDTO(entity);
    }

    // =========================
    // ENTITY → DTO MAPPER
    // =========================
    private PropertyAmenityDTO mapToDTO(PropertyAmenity entity) {

        PropertyAmenityDTO dto = new PropertyAmenityDTO();
        dto.setPropertyAmenityId(entity.getPropertyAmenityId());
        dto.setPropertyId(entity.getPropertyId());
        dto.setAmenityId(entity.getAmenityId());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setNotes(entity.getNotes());

        return dto;
    }

    public ApiResponse<PropertyAmenityDTO> addAmenityToProperty(PropertyAmenity amenity) {
        return null;
    }

}
