package com.aao.controller;

import com.aao.dto.PropertyDto;
import com.aao.entity.Property;
import com.aao.entity.PropertyImage;
import com.aao.response.ApiResponse;
import com.aao.serviceImpl.PropertyImageService;
import com.aao.serviceInterface.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    
    private final PropertyImageService propertyImageService;

    @PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
    public ResponseEntity<ApiResponse<PropertyDto>> addProperty(@RequestBody PropertyDto propertyDto) {
        ApiResponse<PropertyDto> response = propertyService.addProperty(propertyDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public ResponseEntity<ApiResponse<List<PropertyDto>>> getAllProperties() {
        ApiResponse<List<PropertyDto>> response = propertyService.getAllProperties();
        if (response.getStatusCode() == 204) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public ResponseEntity<ApiResponse<PropertyDto>> getPropertyById(@PathVariable Long propertyId) {
        ApiResponse<PropertyDto> response = propertyService.getPropertyById(propertyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<PropertyDto>> updateProperty(@PathVariable Long propertyId,
            @RequestBody PropertyDto propertyDto) {
        ApiResponse<PropertyDto> response = propertyService.updateProperty(propertyId, propertyDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<Void>> deleteProperty(@PathVariable Long propertyId) {
        ApiResponse<Void> response = propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{propertyId}/images")
    public ResponseEntity<List<PropertyImage>> getImagesByProperty(@PathVariable Long propertyId) {
        List<PropertyImage> images = propertyImageService.getImagesByProperty(propertyId);
        return ResponseEntity.ok(images);
    }
    
    
    
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PropertyDto>>> searchProperties( @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String categoryType,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice){
    	
    	
    	ApiResponse<List<PropertyDto>> response= propertyService.serachProperty(city, state, categoryType, propertyType, minPrice, maxPrice);
    	return ResponseEntity.status(response.getStatusCode()).body(response);
    	
    }
}
