package com.aao.dto;

import lombok.*;
public class PropertyAmenityRequestDto {
    private Long propertyId;
    private Long amenityId;
    private Boolean isAvailable;
}
public class PropertyAmenityResponseDto {
    private Long id;
    private Long propertyId;
private String propertyName;
    private Long amenityId;
    private String amenityName;
    private Boolean isAvailable;
   private LocalDateTime assignedAt;
}
