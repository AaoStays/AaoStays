package com.aao.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aao.dto.PropertyAmenityRequestDto;
import com.aao.dto.PropertyAmenityResponseDto;
import com.aao.entity.Property;
import com.aao.entity.PropertyAmenity;
import com.aao.repo.PropertyAmenityRepo;
import com.aao.repo.PropertyRepository;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.PropertyAmenityService;
import com.aao.utils.PropertyAmenityMapper;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class PropertyAmenityServiceImpl implements PropertyAmenityService {

    private final PropertyAmenityRepo amenityRepo;
    private final PropertyRepository propertyRepository;
    private final PropertyAmenityMapper amenityMapper;

    @Override
    public ApiResponse<PropertyAmenityResponseDto> addAmenity(
            PropertyAmenityRequestDto amenityRequestDto) {

        boolean exists = amenityRepo.existsByProperty_PropertyIdAndNameIgnoreCase(
        	    amenityRequestDto.getPropertyId(),
        	    amenityRequestDto.getName()
        	);

        if (exists) {
            throw new IllegalArgumentException(
                    "Amenity already exists for this property"
            );
        }

        Property property = propertyRepository.findById(
                amenityRequestDto.getPropertyId()
        ).orElseThrow(() -> new IllegalArgumentException("Property not found"));

        PropertyAmenity amenity =
                amenityMapper.toEntity(amenityRequestDto, property);

        PropertyAmenity saved = amenityRepo.save(amenity);

        return new ApiResponse<>(
                201,
                "Amenity added successfully",
                amenityMapper.toDto(saved)
        );
    }

    @Override
    public ApiResponse<PropertyAmenityResponseDto> getAmenityById(
            Long propertyAmenityId) {

        PropertyAmenity amenity = amenityRepo.findById(propertyAmenityId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Amenity not found"));

        return new ApiResponse<>(
                200,
                "Amenity fetched successfully",
                amenityMapper.toDto(amenity)
        );
    }

	@Override
	public ApiResponse<Void> deletePropertyAmenity(Long id) {
		     PropertyAmenity amenity = amenityRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Amenity not found"));
		     
		     amenityRepo.delete(amenity);;
		
		return new ApiResponse<Void>(200, "Amenity deleted successfully", null);
	}
    
    
}



