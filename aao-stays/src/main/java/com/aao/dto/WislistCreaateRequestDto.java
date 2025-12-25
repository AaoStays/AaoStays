package com.aao.dto;

import lombok.*;

@Data
public class WishlistCreateRequestDto {
    private Long guestId;
    private Long propertyId;
    private String notes;
}
