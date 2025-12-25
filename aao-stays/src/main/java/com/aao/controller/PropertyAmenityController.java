package com.aao.controller;

import com.aao.dto.PropertyAmenityDTO;
import com.aao.serviceInterface.IPropertyAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property-amenities")
@RequiredArgsConstructor
public class PropertyAmenityController {

    private final IPropertyAmenityService propertyAmenityService;

    @PostMapping
    public ResponseEntity<PropertyAmenityDTO> addAmenity(
            @RequestBody PropertyAmenityDTO dto) {

        return ResponseEntity.ok(
                propertyAmenityService.addAmenityToProperty(dto)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PropertyAmenityDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable) {

        return ResponseEntity.ok(
                propertyAmenityService.updateAmenityStatus(id, isAvailable)
        );
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyAmenityDTO>> getByProperty(
            @PathVariable Long propertyId) {

        return ResponseEntity.ok(
                propertyAmenityService.getAmenitiesByProperty(propertyId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyAmenityDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                propertyAmenityService.getById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        propertyAmenityService.deletePropertyAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
