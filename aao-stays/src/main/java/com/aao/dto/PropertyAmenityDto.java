package com.aao.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyAmenityDTO {

    private Long propertyAmenityId;
    private Long propertyId;
    private Long amenityId;
    private Boolean isAvailable;
    private String notes;
}
