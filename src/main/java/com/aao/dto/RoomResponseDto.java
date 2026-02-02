package com.aao.dto;

import com.aao.entity.RoomStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoomResponseDto {
    private Long roomId;
    private Long propertyId;
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
    private RoomStatus roomStatus;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RoomImageDto> images;
}
