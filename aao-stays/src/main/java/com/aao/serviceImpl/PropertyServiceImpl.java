package com.aao.serviceImpl;

import com.aao.dto.PropertyDto;
import com.aao.entity.ApprovalStatus;
import com.aao.entity.Host;
import com.aao.entity.Property;
import com.aao.entity.PropertyStatus;
import com.aao.repo.HostRepo;
import com.aao.repo.PropertyRepository;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.PropertyService;
import com.aao.utils.PropertyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;
	private final HostRepo hostRepo;
	private final PropertyMapper propertyMapper;

	@Override
	@Transactional
	public ApiResponse<PropertyDto> addProperty(PropertyDto propertyDto, Long hostId) {

	    Host host = hostRepo.findById(hostId)
	            .orElseThrow(() ->
	                    new IllegalArgumentException("Host not found with ID: " + hostId)
	            );

	    Property property = propertyMapper.toEntity(propertyDto);
	    property.setHost(host);

	    property.setEmployeeId(propertyDto.getEmployeeId());

	    if (property.getPropertyStatus() == null) {
	        property.setPropertyStatus(PropertyStatus.PENDING_APPROVAL);
	    }
	    if (property.getApprovalStatus() == null) {
	        property.setApprovalStatus(ApprovalStatus.PENDING);
	    }

	    Property savedProperty = propertyRepository.save(property);

	    host.setTotalProperties(host.getTotalProperties() + 1);
	    hostRepo.save(host);

	    return new ApiResponse<>(
	            201,
	            "Property created successfully",
	            propertyMapper.toDto(savedProperty)
	    );
	}


	@Override
	@Transactional(readOnly = true)
	public ApiResponse<List<PropertyDto>> getAllProperties() {
		List<Property> properties = propertyRepository.findAll();
		List<PropertyDto> propertyDtos = properties.stream().map(propertyMapper::toDto).collect(Collectors.toList());

		if (propertyDtos.isEmpty()) {
			return new ApiResponse<>(204, "No properties found", propertyDtos);
		}

		return new ApiResponse<>(200, "Properties retrieved successfully", propertyDtos);
	}

	@Override
	@Transactional(readOnly = true)
	public ApiResponse<PropertyDto> getPropertyById(Long propertyId) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));
		return new ApiResponse<>(200, "Property details retrieved successfully", propertyMapper.toDto(property));
	}

	@Override
	@Transactional
	public ApiResponse<PropertyDto> updateProperty(Long propertyId, PropertyDto propertyDto) {
		Property existingProperty = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));

		if (propertyDto.getPropertyName() != null)
			existingProperty.setPropertyName(propertyDto.getPropertyName());
		if (propertyDto.getPropertyType() != null)
			existingProperty.setPropertyType(propertyDto.getPropertyType());
		if (propertyDto.getCategoryType() != null)
			existingProperty.setCategoryType(propertyDto.getCategoryType());
		if (propertyDto.getDescription() != null)
			existingProperty.setDescription(propertyDto.getDescription());
		if (propertyDto.getAddressLine1() != null)
			existingProperty.setAddressLine1(propertyDto.getAddressLine1());
		if (propertyDto.getCity() != null)
			existingProperty.setCity(propertyDto.getCity());
		if (propertyDto.getState() != null)
			existingProperty.setState(propertyDto.getState());
		if (propertyDto.getCountry() != null)
			existingProperty.setCountry(propertyDto.getCountry());
		if (propertyDto.getPostalCode() != null)
			existingProperty.setPostalCode(propertyDto.getPostalCode());
		if (propertyDto.getPricePerNight() != null)
			existingProperty.setPricePerNight(propertyDto.getPricePerNight());
		if (propertyDto.getMaxGuests() != null)
			existingProperty.setMaxGuests(propertyDto.getMaxGuests());

		// Add more fields as needed for update, for now updating common ones

		existingProperty.setUpdatedAt(LocalDateTime.now());

		Property updatedProperty = propertyRepository.save(existingProperty);
		return new ApiResponse<>(200, "Property updated successfully", propertyMapper.toDto(updatedProperty));
	}

	@Override
	@Transactional
	public ApiResponse<Void> deleteProperty(Long propertyId) {
		if (!propertyRepository.existsById(propertyId)) {
			throw new IllegalArgumentException("Property not found with ID: " + propertyId);
		}
		propertyRepository.deleteById(propertyId);
		return new ApiResponse<>(200, "Property deleted successfully", null);
	}

	@Override
	public ApiResponse<List<PropertyDto>> serachProperty(String city, String state, String categoryType,
			String propertyType, BigDecimal minPrice, BigDecimal maxPrice) {

		city = (city == null || city.trim().isEmpty()) ? null : city.trim();
		state = (state == null || state.trim().isEmpty()) ? null : state.trim();
		categoryType = (categoryType == null || categoryType.trim().isEmpty()) ? null : categoryType.trim();
		propertyType = (propertyType == null || propertyType.trim().isEmpty()) ? null : propertyType.trim();

		List<Property> properties = propertyRepository.searchProperties(city, state, categoryType, propertyType,
				minPrice, maxPrice);

		if (properties.isEmpty()) {
			return new ApiResponse<>(404, "No properties found", null);
		}

		List<PropertyDto> dtoList = properties.stream().map(propertyMapper::toDto).toList();

		return new ApiResponse<>(200, "Properties fetched successfully", dtoList);
	}

	@Override
	public ApiResponse<PropertyDto> updatePropertyStaus(Long PropertyId, PropertyStatus newStatus) {

		Property property = propertyRepository.findById(PropertyId)
				.orElseThrow(() -> new IllegalArgumentException("\"Property not found with ID: \" + propertyId"));

		PropertyStatus oldStatus = property.getPropertyStatus();
		Host host = property.getHost();

		property.setPropertyStatus(newStatus);
		Property savedProperty = propertyRepository.save(property);

		if (oldStatus != PropertyStatus.ACTIVE && newStatus == PropertyStatus.ACTIVE) {
			host.setActiveProperties(host.getActiveProperties() + 1);
		}

		if (oldStatus == PropertyStatus.ACTIVE && newStatus != PropertyStatus.ACTIVE) {
			host.setActiveProperties(host.getActiveProperties() - 1);
		}

		return new ApiResponse<>(200, "Property status updated successfully", propertyMapper.toDto(savedProperty));
	}

	@Override
	@Transactional
	public ApiResponse<List<PropertyDto>> getPropertyByHostId(Long hostId) {
	    
	 
	    Host host = hostRepo.findById(hostId)
	            .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));
	    
	    List<Property> properties = propertyRepository.findByHost_HostId(hostId);
	    
	    if (properties.isEmpty()) {
	        return new ApiResponse<>(404, "No properties found for this host", null);
	    }
	    
	    List<PropertyDto> propertyDtos = properties.stream()
	            .map(propertyMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return new ApiResponse<>(200, "Properties fetched successfully", propertyDtos);
	}
}
