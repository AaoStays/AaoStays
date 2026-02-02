package com.aao.dto;

import lombok.Data;

@Data

public class RoomAmenityRequest {

private Long roomId;
private String amenityName;
private String amenityIcon;
private Boolean isAvailable;
private String notes;
}