package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostResponseDto {
    
	
	 private Long hostId;
	    private Long userId;

	   
	    private String fullName;
	    private String email;
	    private String phoneNumber;
	    private String address;
	    private String profilePictureUrl;

	  
	    private String bio;
	    private String languagesSpoken;
	    private String governmentIdType;
	    private String governmentIdNumber;

	    private Boolean isProfileCompleted;
	    
	    private Integer totalProperties;
}
