package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for creating and updating booking requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

	  private Long propertyId;        
	    private Long roomId;            

	    private Long userId;           

	    private Long couponId;          
	    private String couponCode;     

	    private LocalDate checkInDate;  
	    private LocalDate checkOutDate; 

	    private Integer numberOfGuests;  
	    @Builder.Default
	    private Integer numberOfAdults = 1; 
	    @Builder.Default
	    private Integer numberOfChildren = 0; 
	    @Builder.Default
	    private Integer numberOfInfants = 0;  

	    private String specialRequests;  
}
