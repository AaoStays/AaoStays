package com.aao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aao.dto.PropertyAmenityRequestDto;
import com.aao.dto.PropertyAmenityResponseDto;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.PropertyAmenityService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/PropertyAmenity")
public class PropertyAmenityController {
 
	
   private final PropertyAmenityService amenityService;
	@PostMapping("/add")
	@PreAuthorize("hasAnyRole('HOST','ADMIN')")
	public ResponseEntity<ApiResponse<PropertyAmenityResponseDto>> addAmenity(@RequestBody  PropertyAmenityRequestDto amenityRequestDto){
		 ApiResponse<PropertyAmenityResponseDto> response= amenityService.addAmenity(amenityRequestDto);
		 
		return  ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@GetMapping("/getAmneity")
	@PreAuthorize("hasAnyRole('HOST','ADMIN')")
	public ResponseEntity<ApiResponse<PropertyAmenityResponseDto>> getAmenity(Long propertyAmenityId){
		
		ApiResponse<PropertyAmenityResponseDto> response= amenityService.getAmenityById(propertyAmenityId);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('HOST','ADMIN')")
	public ResponseEntity<ApiResponse<Void>> deletePropertyAmenity(Long id){
		
		ApiResponse<Void> response=amenityService.deletePropertyAmenity(id);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
}
