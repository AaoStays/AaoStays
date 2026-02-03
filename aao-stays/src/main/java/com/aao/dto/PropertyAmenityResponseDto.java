package com.aao.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAmenityResponseDto {

	private Long id;
	private String name;
	private boolean isAvailabel;
    private String notes;
}
