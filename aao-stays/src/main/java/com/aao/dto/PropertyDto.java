package com.aao.dto;

import com.aao.entity.ApprovalStatus;
import com.aao.entity.PropertyStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class PropertyDto {
    private Long propertyId;
    private Long employeeId;
    private Long adminId;
    private String propertyName;
    private String propertyType;
    private String categoryType;
    private String description;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationUrl;
    private String mapEmbedUrl;
    private Integer baseGuests;
    private Integer maxGuests;
    private Boolean extraGuestAllowed;
    private BigDecimal extraGuestFee;
    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;
    private Integer restrooms;
    private String kitchenType;
    private Boolean petsAllowed;
    private Boolean smokingAllowed;
    private Boolean eventsAllowed;
    private BigDecimal pricePerNight;
    private BigDecimal cleaningFee;
    private BigDecimal serviceFee;
    private BigDecimal weekendPrice;
    private BigDecimal weeklyDiscountPercent;
    private BigDecimal monthlyDiscountPercent;
    private String currency;
    private Integer minimumStay;
    private Integer maximumStay;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String cancellationPolicy;
    private Boolean instantBooking;
    private PropertyStatus propertyStatus;
    private ApprovalStatus approvalStatus;
    private Boolean isActive;
    private Boolean isFeatured;
    private BigDecimal ratingAverage;
    private Integer totalReviews;
    private Integer totalBookings;
    private Integer viewsCount;
    private Integer wishlistCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private List<PropertyImageDto> images;

}
