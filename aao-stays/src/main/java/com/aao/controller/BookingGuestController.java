@RestController
@RequestMapping("/api/v1/property-amenities")
@RequiredArgsConstructor
public class PropertyAmenityController {

    private final IPropertyAmenityService service;

    @PostMapping
    public ResponseEntity<PropertyAmenityResponseDto> assignAmenity(
            @RequestBody PropertyAmenityRequestDto dto) {
        return ResponseEntity.ok(service.assignAmenity(dto));
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<PropertyAmenityResponseDto> updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable) {
        return ResponseEntity.ok(
                service.updateAvailability(id, isAvailable));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyAmenityResponseDto>> getByProperty(
            @PathVariable Long propertyId) {
        return ResponseEntity.ok(
                service.getAmenitiesByProperty(propertyId));
    }

    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<List<PropertyAmenityResponseDto>> getByAmenity(
            @PathVariable Long amenityId) {
        return ResponseEntity.ok(
                service.getPropertiesByAmenity(amenityId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAmenity(
            @RequestParam Long propertyId,
            @RequestParam Long amenityId) {
        service.removeAmenity(propertyId, amenityId);
        return ResponseEntity.noContent().build();
    }
}
