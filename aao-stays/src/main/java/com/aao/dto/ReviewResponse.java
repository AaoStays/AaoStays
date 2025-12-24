package com.aao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Long bookingId;
    private Long propertyId;
    private Long guestId;
    private BigDecimal overallRating;
    private BigDecimal cleanlinessRating;
    private BigDecimal accuracyRating;
    private BigDecimal communicationRating;
    private BigDecimal locationRating;
    private BigDecimal valueRating;
    private BigDecimal checkInRating;
    private String reviewTitle;
    private String reviewText;
    private String hostResponse;
    private LocalDateTime hostResponseDate;
    private Boolean isVerified;
    private Boolean isVisible;
    private Boolean isFeatured;
    private Integer helpfulCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String guestName; // optional for display
}
