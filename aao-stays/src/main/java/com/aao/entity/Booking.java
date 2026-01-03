package com.aao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long propertyId;
    private Long roomId;
    private Long guestId;
    private Long couponId;
    private String checkInDate;
    private String checkOutDate;
    private Integer numberOfGuests;
    private Integer numberOfNights;
    private Double basePrice;
    private Double extraGuestCharges;
    private Double discountAmount;
    private Double totalAmount;
    private String bookingStatus;
    private String specialRequests;
    private String cancellationReason;
    private String cancelledAt;
    private Long cancelledBy;
    private String createdAt;
    private String updatedAt;
    private String confirmedAt;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingGuest> bookingGuests;
}
