package com.aao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="roomAmenity")
public class RoomAmenity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)

@Column(name="room_amenity_id")
private Long roomAmenityId;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "room_id", nullable=false)
private Room room;

@Column(name = "amenity_name",length=100)
private String amenityName;

@Column(name = "amenity_icon")
private String amenityIcon;

@Column(name = "is_available")
private Boolean isAvailable=true;

@Column(name = "notes")
private String notes;

}