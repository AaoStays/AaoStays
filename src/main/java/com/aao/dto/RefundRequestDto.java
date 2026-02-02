package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for processing refund requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDto {

    private BigDecimal refundAmount; // Amount to refund
    private String refundMethod; // e.g., "original_payment_method", "bank_transfer"
    private String refundReason; // Reason for refund
}
