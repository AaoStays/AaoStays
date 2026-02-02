package com.aao.dto;

public class RoomAmenityResponse {

private Long roomAmenityId;
private Long roomId;
private String amenityName;
private String amenityIcon;
private Boolean isAvailable;
private String notes;

// getters & setters
public Long getRoomAmenityId() { return roomAmenityId; }
public void setRoomAmenityId(Long roomAmenityId) { this.roomAmenityId = roomAmenityId; }

public Long getRoomId() { return roomId; }
public void setRoomId(Long roomId) { this.roomId = roomId; }

public String getAmenityName() { return amenityName; }
public void setAmenityName(String amenityName) { this.amenityName = amenityName; }

public String getAmenityIcon() { return amenityIcon; }
public void setAmenityIcon(String amenityIcon) { this.amenityIcon = amenityIcon; }

public Boolean getIsAvailable() { return isAvailable; }
public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

public String getNotes() { return notes; }
public void setNotes(String notes) { this.notes = notes; }
}