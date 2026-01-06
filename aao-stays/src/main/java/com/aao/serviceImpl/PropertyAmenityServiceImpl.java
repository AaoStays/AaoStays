@Service
@RequiredArgsConstructor
public class PropertyAmenityService {

    private final PropertyRepository propertyRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyAmenityRepository propertyAmenityRepository;

    public PropertyAmenityResponseDto assignAmenity(
            AssignAmenityRequestDto request) {

        if (propertyAmenityRepository
                .existsByProperty_PropertyIdAndAmenity_AmenityId(
                        request.getPropertyId(),
                        request.getAmenityId())) {
            throw new RuntimeException("Amenity already assigned to property");
        }

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Amenity amenity = amenityRepository.findById(request.getAmenityId())
                .orElseThrow(() -> new RuntimeException("Amenity not found"));

        PropertyAmenity propertyAmenity = PropertyAmenity.builder()
                .property(property)
                .amenity(amenity)
                .isAvailable(request.getIsAvailable())
                .build();

        PropertyAmenity saved =
                propertyAmenityRepository.save(propertyAmenity);

        return mapToDto(saved);
    }

    public List<PropertyAmenityResponseDto> getAmenitiesByProperty(
            Long propertyId) {

        return propertyAmenityRepository
                .findByProperty_PropertyId(propertyId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public void removeAmenity(Long propertyId, Long amenityId) {
        propertyAmenityRepository
                .deleteByProperty_PropertyIdAndAmenity_AmenityId(
                        propertyId, amenityId);
    }

    private PropertyAmenityResponseDto mapToDto(PropertyAmenity pa) {
        return PropertyAmenityResponseDto.builder()
                .propertyAmenityId(pa.getPropertyAmenityId())
                .propertyId(pa.getProperty().getPropertyId())
                .amenityId(pa.getAmenity().getAmenityId())
                .amenityName(pa.getAmenity().getAmenityName())
                .isAvailable(pa.getIsAvailable())
                .assignedAt(pa.getAssignedAt())
                .build();
    }
}
