package com.aao.dto;

import com.aao.entity.Property;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsRequestDto {
     
	
	private Long contactId;	
	@NotNull
	private Long propertyId;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;
	@NotBlank(message = "name  is required")
    @Size(min = 2, max = 50, message = "Contact name must be between 2 and 50 characters")
	private String contactName;
	@NotBlank(message = "Primary mobile number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Primary mobile must be a valid 10-digit Indian number"
    )
	private String  primaryMobile;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Alternate mobile must be a valid 10-digit Indian number"
    )
	private String alternateMobile;
    @NotNull(message = "Preferred contact method is required")
    private String preferredContactMethod = "PHONE";
    
    private Property property;

	
}
