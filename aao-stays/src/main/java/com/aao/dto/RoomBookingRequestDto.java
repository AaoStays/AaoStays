package com.aao.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class RoomBookingRequestDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;
}
