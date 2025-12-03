package com.aao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "PROPERTY", indexes = {
        @Index(name = "idx_host_id", columnList = "host_id"),
        @Index(name = "idx_city", columnList = "city"),
        @Index(name = "idx_state", columnList = "state"),
        @Index(name = "idx_country", columnList = "country"),
        @Index(name = "idx_property_status", columnList = "property_status"),
        @Index(name = "idx_is_active", columnList = "is_active"),
        @Index(name = "idx_is_featured", columnList = "is_featured"),
        @Index(name = "idx_price", columnList = "price_per_night"),
        @Index(name = "idx_rating", columnList = "rating_average"),
        @Index(name = "idx_location", columnList = "latitude, longitude"),
        @Index(name = "idx_instant_booking", columnList = "instant_booking")
})
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    /* 🔥 Prevent Lazy Loading Recursion */
    @Column(name = "host_id", insertable = false, updatable = false)
    private Long hostId;

    @Column(name = "admin_id", insertable = false, updatable = false)
    private Long adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    @ToString.Exclude
    @JsonIgnoreProperties({"properties", "hibernateLazyInitializer"})
    private Host host;

    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private Admin admin;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "property_type", length = 100)
    private String propertyType;

    @Column(name = "category_type", length = 100)
    private String categoryType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country = "India";

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "location_url", length = 512)
    private String locationUrl;

    @Column(name = "map_embed_url", length = 512)
    private String mapEmbedUrl;

    @Column(name = "base_guests")
    private Integer baseGuests = 1;

    @Column(name = "max_guests")
    private Integer maxGuests = 1;

    @Column(name = "extra_guest_allowed")
    private Boolean extraGuestAllowed = false;

    @Column(name = "extra_guest_fee", precision = 10, scale = 2)
    private BigDecimal extraGuestFee = BigDecimal.ZERO;

    @Column(name = "bedrooms")
    private Integer bedrooms = 0;

    @Column(name = "beds")
    private Integer beds = 0;

    @Column(name = "bathrooms")
    private Integer bathrooms = 0;

    @Column(name = "restrooms")
    private Integer restrooms = 0;

    @Column(name = "kitchen_type", length = 50)
    private String kitchenType;

    @Column(name = "pets_allowed")
    private Boolean petsAllowed = false;

    @Column(name = "smoking_allowed")
    private Boolean smokingAllowed = false;

    @Column(name = "events_allowed")
    private Boolean eventsAllowed = false;

    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(name = "cleaning_fee", precision = 10, scale = 2)
    private BigDecimal cleaningFee = BigDecimal.ZERO;

    @Column(name = "service_fee", precision = 10, scale = 2)
    private BigDecimal serviceFee = BigDecimal.ZERO;

    @Column(name = "weekend_price", precision = 10, scale = 2)
    private BigDecimal weekendPrice;

    @Column(name = "weekly_discount_percent", precision = 5, scale = 2)
    private BigDecimal weeklyDiscountPercent = BigDecimal.ZERO;

    @Column(name = "monthly_discount_percent", precision = 5, scale = 2)
    private BigDecimal monthlyDiscountPercent = BigDecimal.ZERO;

    @Column(name = "currency", length = 10)
    private String currency = "INR";

    @Column(name = "minimum_stay")
    private Integer minimumStay = 1;

    @Column(name = "maximum_stay")
    private Integer maximumStay = 365;

    @Column(name = "check_in_time")
    private LocalTime checkInTime = LocalTime.of(14, 0);

    @Column(name = "check_out_time")
    private LocalTime checkOutTime = LocalTime.of(11, 0);

    @Column(name = "cancellation_policy", length = 50)
    private String cancellationPolicy = "MODERATE";

    @Column(name = "instant_booking")
    private Boolean instantBooking = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_status")
    private PropertyStatus propertyStatus = PropertyStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "rating_average", precision = 3, scale = 2)
    private BigDecimal ratingAverage = BigDecimal.ZERO;

    @Column(name = "total_reviews")
    private Integer totalReviews = 0;

    @Column(name = "total_bookings")
    private Integer totalBookings = 0;

    @Column(name = "views_count")
    private Integer viewsCount = 0;

    @Column(name = "wishlist_count")
    private Integer wishlistCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PropertyImage> images;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private ContactDetails contactDetails;



  

    
    public void addImage(PropertyImage image) {
        if (images == null) {
            images = new java.util.ArrayList<>();
        }
        images.add(image);
        image.setProperty(this);
    }

    public void removeImage(PropertyImage image) {
        if (images != null) {
            images.remove(image);
        }
        image.setProperty(null);
    }

}
