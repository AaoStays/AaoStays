// package com.aao.dto;

// import lombok.*;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
package com.aao.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingGuestRequestDto {

    private Long propertyId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Integer numberOfGuests;

    // Guest details
    private String guestFullName;
    private String guestEmail;
    private String guestPhone;
    private Integer guestAge;
    private String guestIdProofType;
    private String guestIdProofNumber;
}
