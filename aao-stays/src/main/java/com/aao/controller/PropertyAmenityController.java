@RestController
@RequestMapping("/api/v1/property-amenities")
@RequiredArgsConstructor
public class PropertyAmenityController {

    private final PropertyAmenityService service;

    @PostMapping
    public ResponseEntity<PropertyAmenityResponseDto> assignAmenity(
            @RequestBody AssignAmenityRequestDto request) {
        return ResponseEntity.ok(service.assignAmenity(request));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyAmenityResponseDto>> getAmenities(
            @PathVariable Long propertyId) {
        return ResponseEntity.ok(service.getAmenitiesByProperty(propertyId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAmenity(
            @RequestParam Long propertyId,
            @RequestParam Long amenityId) {
        service.removeAmenity(propertyId, amenityId);
        return ResponseEntity.noContent().build();
    }
}
