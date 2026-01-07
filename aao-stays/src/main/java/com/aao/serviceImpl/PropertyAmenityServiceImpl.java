@Service
@RequiredArgsConstructor
@Transactional
public class PropertyAmenityServiceImpl
        implements IPropertyAmenityService {

    private final PropertyRepository propertyRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyAmenityRepository repository;

    @Override
    public PropertyAmenityResponseDto assignAmenity(
            PropertyAmenityRequestDto dto) {

        if (repository.existsByProperty_PropertyIdAndAmenity_AmenityId(
                dto.getPropertyId(), dto.getAmenityId())) {
            throw new RuntimeException("Amenity already assigned to property");
        }

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Amenity amenity = amenityRepository.findById(dto.getAmenityId())
                .orElseThrow(() -> new RuntimeException("Amenity not found"));

        PropertyAmenity pa = PropertyAmenity.builder()
                .property(property)
                .amenity(amenity)
                .isAvailable(
                        dto.getIsAvailable() != null
                                ? dto.getIsAvailable()
                                : true)
                .build();

        return mapToDto(repository.save(pa));
    }

    @Override
    public PropertyAmenityResponseDto updateAvailability(
            Long id, Boolean isAvailable) {

        PropertyAmenity pa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        pa.setIsAvailable(isAvailable);
        return mapToDto(repository.save(pa));
    }

    @Override
    public List<PropertyAmenityResponseDto> getAmenitiesByProperty(
            Long propertyId) {

        return repository.findByProperty_PropertyId(propertyId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<PropertyAmenityResponseDto> getPropertiesByAmenity(
            Long amenityId) {

        return repository.findByAmenity_AmenityId(amenityId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void removeAmenity(Long propertyId, Long amenityId) {
        repository.deleteByProperty_PropertyIdAndAmenity_AmenityId(
                propertyId, amenityId);
    }

    private PropertyAmenityResponseDto mapToDto(PropertyAmenity pa) {
        return PropertyAmenityResponseDto.builder()
                .id(pa.getPropertyAmenityId())
                .propertyId(pa.getProperty().getPropertyId())
                .propertyName(pa.getProperty().getPropertyName())
                .amenityId(pa.getAmenity().getAmenityId())
                .amenityName(pa.getAmenity().getAmenityName())
                .isAvailable(pa.getIsAvailable())
                .assignedAt(pa.getAssignedAt())
                .build();
    }
}
