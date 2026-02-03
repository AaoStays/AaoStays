package com.aao.serviceInterface;

import com.aao.dto.PropertyReviewResponse;
import com.aao.dto.ReviewRequest;
import com.aao.dto.ReviewResponse;
import com.aao.response.ApiResponse;

public interface IReviewService {
    ApiResponse<ReviewResponse> addReview(ReviewRequest request, String currentUserEmail);

    ApiResponse<ReviewResponse> updateReview(Long reviewId, ReviewRequest request, String currentUserEmail);

    ApiResponse<String> deleteReview(Long reviewId, String currentUserEmail);

    ApiResponse<ReviewResponse> hostReply(Long reviewId, String hostResponse, String currentUserEmail);

    ApiResponse<String> featureReview(Long reviewId, boolean feature);

    ApiResponse<String> hideReview(Long reviewId, boolean hide);

    ApiResponse<PropertyReviewResponse> getReviewsByProperty(Long propertyId, boolean includeHidden);

    ApiResponse<ReviewResponse> getReviewById(Long reviewId);
}
