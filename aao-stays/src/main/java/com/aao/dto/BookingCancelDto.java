package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for cancelling a booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCancelDto {

    private String cancellationReason; // Required - reason for cancellation
    private Boolean refundRequested; // Whether refund is requested
}
