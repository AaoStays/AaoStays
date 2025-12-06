package com.aao.entity;

/**
 * Enum representing the payment status of a booking.
 */
public enum PaymentStatus {
    PENDING, // Payment not yet received
    PARTIAL, // Partial payment received
    PAID, // Full payment received
    REFUNDED // Payment refunded
}
