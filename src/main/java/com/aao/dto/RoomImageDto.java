package com.aao.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomImageDto {
    private Long imageId;
    private String imageUrl;
    private String imageCaption;
    private Integer displayOrder;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
}
