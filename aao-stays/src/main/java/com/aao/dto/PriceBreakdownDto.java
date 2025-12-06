package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for price breakdown response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceBreakdownDto {

    private Integer numberOfNights;
    private BigDecimal basePrice;
    private BigDecimal extraGuestCharges;
    private BigDecimal cleaningFee;
    private BigDecimal serviceFee;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
}
