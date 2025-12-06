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

    private Long propertyId; // Required
    private Long roomId; // Optional (can book entire property)
    private Long userId; // Required - guest making the booking
    private Long couponId; // Optional - for discounts

    private LocalDate checkInDate; // Required
    private LocalDate checkOutDate; // Required

    private Integer numberOfGuests; // Required - total guest count
    private Integer numberOfAdults; // Default 1
    private Integer numberOfChildren; // Default 0
    private Integer numberOfInfants; // Default 0

    private String specialRequests; // Optional
    private String couponCode; // Optional - alternative to couponId
}
