package com.aao.dto;

import lombok.Data;

@Data
public class PropertyAmenityRequestDto {
  
	private Long propertyId;
	private String name;
	private boolean isAvailable;
	private String notes;
	
}
