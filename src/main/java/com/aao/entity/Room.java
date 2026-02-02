package com.aao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({ "rooms", "host", "hibernateLazyInitializer" })
    private Property property;

    @Column(name = "created_by_employee_id")
    private Long createdByEmployeeId;

    @Column(name = "updated_by_employee_id")
    private Long updatedByEmployeeId;

    @Column(name = "room_number", length = 50)
    private String roomNumber;

    @Column(name = "room_type", length = 100)
    private String roomType;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_description", columnDefinition = "TEXT")
    private String roomDescription;

    @Column(name = "base_guests")
    private Integer baseGuests = 1;

    @Column(name = "max_guests")
    private Integer maxGuests = 1;

    @Column(name = "extra_guest_allowed")
    private Boolean extraGuestAllowed = false;

    @Column(name = "extra_guest_fee", precision = 10, scale = 2)
    private BigDecimal extraGuestFee = BigDecimal.ZERO;

    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(name = "bed_type", length = 50)
    private String bedType;

    @Column(name = "bed_count")
    private Integer bedCount = 1;

    @Column(name = "room_size_sqft")
    private Integer roomSizeSqft;

    @Column(name = "has_balcony")
    private Boolean hasBalcony = false;

    @Column(name = "has_window")
    private Boolean hasWindow = true;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status")
    private RoomStatus roomStatus = RoomStatus.AVAILABLE;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<RoomImage> images;

    public void addImage(RoomImage image) {
        if (images == null) {
            images = new java.util.ArrayList<>();
        }
        images.add(image);
        image.setRoom(this);
    }

    public void removeImage(RoomImage image) {
        if (images != null) {
            images.remove(image);
        }
        image.setRoom(null);
    }
}
