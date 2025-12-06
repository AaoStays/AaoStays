package com.aao.dto;

import com.aao.entity.BookingStatus;
import com.aao.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Booking responses containing complete booking information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long bookingId;
    private String bookingReference;

    // Property and Room information
    private Long propertyId;
    private String propertyName;
    private Long roomId;
    private String roomName;

    // User information
    private Long userId;
    private String guestName;
    private String guestEmail;

    // Booking dates
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfNights;

    // Guest counts
    private Integer numberOfGuests;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private Integer numberOfInfants;

    // Pricing breakdown
    private BigDecimal basePrice;
    private BigDecimal extraGuestCharges;
    private BigDecimal cleaningFee;
    private BigDecimal serviceFee;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;

    // Status
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;

    // Additional info
    private String specialRequests;
    private String cancellationReason;
    private BigDecimal refundAmount;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private LocalDateTime cancelledAt;
    private LocalDateTime refundProcessedAt;
}
