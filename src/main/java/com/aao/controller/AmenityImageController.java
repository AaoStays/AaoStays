package com.aao.controller;

import com.aao.dto.AmenityImageRequest;
import com.aao.dto.AmenityImageResponse;
import com.aao.enums.RelatedType;
import com.aao.serviceInterface.AmenityImageService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenity-images")
public class AmenityImageController {

    private final AmenityImageService service;

    public AmenityImageController(AmenityImageService service) {
        this.service = service;
    }

    @PostMapping
    public AmenityImageResponse save(@RequestBody AmenityImageRequest dto) {
        return service.save(dto);
    }

    @GetMapping("/property/{propertyId}")
    public List<AmenityImageResponse> getByProperty(@PathVariable Long propertyId) {
        return service.getByPropertyId(propertyId);
    }

    @GetMapping("/property/{propertyId}/type/{type}")
    public List<AmenityImageResponse> getByPropertyAndType(
            @PathVariable Long propertyId,
            @PathVariable RelatedType type) {
        return service.getByPropertyAndType(propertyId, type);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}