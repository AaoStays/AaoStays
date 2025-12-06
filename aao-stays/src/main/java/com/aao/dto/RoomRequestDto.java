package com.aao.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequestDto {
    private String roomNumber;
    private String roomType;
    private String roomName;
    private String roomDescription;
    private Integer baseGuests;
    private Integer maxGuests;
    private Boolean extraGuestAllowed;
    private BigDecimal extraGuestFee;
    private BigDecimal pricePerNight;
    private String bedType;
    private Integer bedCount;
    private Integer roomSizeSqft;
    private Boolean hasBalcony;
    private Boolean hasWindow;
    private Integer floorNumber;
}
