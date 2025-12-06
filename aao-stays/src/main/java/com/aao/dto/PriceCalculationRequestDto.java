package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for requesting price calculation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationRequestDto {

    private Long propertyId; 
    private Long roomId;
    private LocalDate checkInDate; // Required
    private LocalDate checkOutDate; // Required
    private Integer numberOfGuests; // Required
    private String couponCode; // Optional
}
