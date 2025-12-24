package com.aao.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long bookingId;
    private Long propertyId;
    private BigDecimal overallRating;
    private BigDecimal cleanlinessRating;
    private BigDecimal accuracyRating;
    private BigDecimal communicationRating;
    private BigDecimal locationRating;
    private BigDecimal valueRating;
    private BigDecimal checkInRating;
    private String reviewTitle;
    private String reviewText;

    // for host reply
    private String hostResponse;
}
