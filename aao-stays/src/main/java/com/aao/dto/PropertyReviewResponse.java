package com.aao.dto;

import java.util.List;
import lombok.Data;

@Data
public class PropertyReviewResponse {
    private Double averageRating;
    private Long totalReviews;
    private List<ReviewResponse> reviews;
}
