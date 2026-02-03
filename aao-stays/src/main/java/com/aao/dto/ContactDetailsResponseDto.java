package com.aao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ContactDetailsResponseDto {
	
	 @NotNull
	    private Long contactId;

	    @NotBlank
	    @Email
	    private String email;

	    private String contactName;
	}
	
	


