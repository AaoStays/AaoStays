package com.aao.dto;

import com.aao.entity.VerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Host entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostDto {
    private Long hostId;
    private Long userId; // Kept for reference/creation
    private String hostFullName; // From User
    private String email; // From User
    private String phoneNumber; // From User
    private String address; // From User

    private String companyName;
    private String gstNumber;

    private String bio;
    private String profilePicture;
    private String languagesSpoken;
    private Boolean isSuperhost;
    private LocalDate hostSince;
    private Boolean isVerified;
    private VerificationStatus verificationStatus;
    private String governmentIdType;
    private String governmentIdNumber;
    private Boolean idVerified;
    private Integer totalProperties;
    private Integer activeProperties;
    private Integer totalBookings;
    private BigDecimal totalEarnings;
    private BigDecimal earningsPerMonth;
    private BigDecimal averageRating;
    private BigDecimal responseRate;
    private Integer responseTime;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isProfileCompleted;
}
