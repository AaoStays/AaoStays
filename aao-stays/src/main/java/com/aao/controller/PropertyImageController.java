package com.aao.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aao.entity.PropertyImage;
import com.aao.serviceImpl.PropertyImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyImageController {
   

	    private final PropertyImageService propertyImageService;

	  
	    @PostMapping("/{propertyId}/images/upload")
	    @PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
	    public ResponseEntity<List<PropertyImage>> uploadImages(
	            @PathVariable Long propertyId,
	            @RequestParam("images") MultipartFile[] images
	    ) {
	        List<PropertyImage> uploaded = propertyImageService.uploadImages(propertyId, images);
	        return ResponseEntity.ok(uploaded);
	    }

	  
	    @PutMapping("/images/{imageId}/primary")
	    public ResponseEntity<PropertyImage> setPrimaryImage(@PathVariable Long imageId) {
	        PropertyImage updated = propertyImageService.setPrimaryImage(imageId);
	        return ResponseEntity.ok(updated);
	    }

	   
	    @DeleteMapping("/images/{imageId}")
	    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
	        propertyImageService.deleteImage(imageId);
	        return ResponseEntity.ok("Image deleted successfully");
	    }
}
