package com.aao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_guest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingGuestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private String guestFullName;
    private String guestEmail;
    private String guestPhone;
    private Integer guestAge;

    private String guestIdProofType;
    private String guestIdProofNumber;
    private String relationshipToBooker;
}
