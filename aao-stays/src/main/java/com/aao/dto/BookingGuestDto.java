package com.aao.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingGuestDto {
    private Long bookingGuestId;
    private Long bookingId;
    private String guestFullName;
    private String guestEmail;
    private String guestPhone;
    private Integer guestAge;
    private String guestIdProofType;
    private String guestIdProofNumber;
    private String relationshipToBooker;
}
