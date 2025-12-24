package com.aao.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponseDto {
    private Long wishlistId;
    private Long guestId;
    private Long propertyId;
    private String notes;
    private LocalDateTime addedAt;
}
