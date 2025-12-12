package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for confirming a booking after payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmDto {

    private String paymentMethod; 
    private String transactionId; 
}
