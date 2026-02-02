	package com.aao.dto;
	
	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	
	
	/**
	 * Data Transfer Object for Host entity.
	 */
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class HostOnboardingRequestDto {
	    private Long hostId;
	    private Long userId; 
	    
	    private String bio;
	   
	    private String languagesSpoken;
	    private String governmentIdType;
	    private String governmentIdNumber;
	}
