package com.aao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
@Table(name = "HOST", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_is_superhost", columnList = "is_superhost"),
        @Index(name = "idx_is_verified", columnList = "is_verified"),
        @Index(name = "idx_average_rating", columnList = "average_rating")
})
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long hostId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // ⭐ FIX: Prevent infinite recursion Property → Host → Property
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Property> properties;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture", length = 512)
    private String profilePicture;

    @Column(name = "languages_spoken", length = 255)
    private String languagesSpoken;

    @Column(name = "is_superhost", nullable = false)
    private Boolean isSuperhost = false;

    @Column(name = "host_since")
    private LocalDate hostSince;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "verification_status", nullable = false)
    private String verificationStatus = "PENDING";

    @Column(name = "government_id_type", length = 50)
    private String governmentIdType;

    @Column(name = "government_id_number", length = 100)
    private String governmentIdNumber;

    @Column(name = "id_verified", nullable = false)
    private Boolean idVerified = false;

    @Column(name = "total_properties", nullable = false)
    private Integer totalProperties;

    @Column(name = "active_properties", nullable = false)
    private Integer activeProperties;

    @Column(name = "total_bookings", nullable = false)
    private Integer totalBookings;

    @Column(name = "total_earnings", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalEarnings;

    @Column(name = "earnings_per_month", precision = 15, scale = 2, nullable = false)
    private BigDecimal earningsPerMonth;

    @Column(name = "average_rating", precision = 3, scale = 2, nullable = false)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "response_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal responseRate = BigDecimal.ZERO;

    @Column(name = "response_time", nullable = false)
    private Integer responseTime = 0;

    @Column(name = "is_profile_completed", nullable = false)
    private Boolean isProfileCompleted = false;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
