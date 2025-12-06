package com.aao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BOOKING")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "booking_reference", unique = true, nullable = false, length = 50)
    private String bookingReference;

    // Relationships
    @Column(name = "property_id", insertable = false, updatable = false)
    private Long propertyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Property property;

    @Column(name = "room_id", insertable = false, updatable = false)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @ToString.Exclude
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Room room;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private User user;

    @Column(name = "coupon_id")
    private Long couponId;

    // Booking dates
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    // Guest information
    @Column(name = "number_of_guests", nullable = false)
    private Integer numberOfGuests;

    @Column(name = "number_of_adults")
    private Integer numberOfAdults = 1;

    @Column(name = "number_of_children")
    private Integer numberOfChildren = 0;

    @Column(name = "number_of_infants")
    private Integer numberOfInfants = 0;

    @Column(name = "number_of_nights", nullable = false)
    private Integer numberOfNights;

    // Pricing
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "extra_guest_charges", precision = 12, scale = 2)
    private BigDecimal extraGuestCharges = BigDecimal.ZERO;

    @Column(name = "cleaning_fee", precision = 12, scale = 2)
    private BigDecimal cleaningFee = BigDecimal.ZERO;

    @Column(name = "service_fee", precision = 12, scale = 2)
    private BigDecimal serviceFee = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // Additional information
    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancelled_by")
    private Long cancelledBy;

    @Column(name = "refund_amount", precision = 12, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "refund_processed_at")
    private LocalDateTime refundProcessedAt;

    // Timestamps
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

    @Column(name = "checked_out_at")
    private LocalDateTime checkedOutAt;

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
