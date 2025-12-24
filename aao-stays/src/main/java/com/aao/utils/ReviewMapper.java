package com.aao.utils;

import java.time.LocalDateTime;

import com.aao.dto.ReviewRequest;
import com.aao.dto.ReviewResponse;
import com.aao.entity.Review;

public class ReviewMapper {

    public static ReviewResponse toResponse(Review r) {
        if (r == null) return null;
        ReviewResponse resp = new ReviewResponse();
        resp.setReviewId(r.getReviewId());
        resp.setBookingId(r.getBookingId());
        resp.setPropertyId(r.getPropertyId());
        resp.setGuestId(r.getGuestId());
        resp.setOverallRating(r.getOverallRating());
        resp.setCleanlinessRating(r.getCleanlinessRating());
        resp.setAccuracyRating(r.getAccuracyRating());
        resp.setCommunicationRating(r.getCommunicationRating());
        resp.setLocationRating(r.getLocationRating());
        resp.setValueRating(r.getValueRating());
        resp.setCheckInRating(r.getCheckInRating());
        resp.setReviewTitle(r.getReviewTitle());
        resp.setReviewText(r.getReviewText());
        resp.setHostResponse(r.getHostResponse());
        resp.setHostResponseDate(r.getHostResponseDate());
        resp.setIsVerified(r.getIsVerified());
        resp.setIsVisible(r.getIsVisible());
        resp.setIsFeatured(r.getIsFeatured());
        resp.setHelpfulCount(r.getHelpfulCount());
        resp.setCreatedAt(r.getCreatedAt());
        resp.setUpdatedAt(r.getUpdatedAt());
        return resp;
    }

    public static Review fromRequest(ReviewRequest req) {
        if (req == null) return null;
        Review r = new Review();
        r.setBookingId(req.getBookingId());
        r.setPropertyId(req.getPropertyId());
        r.setOverallRating(req.getOverallRating());
        r.setCleanlinessRating(req.getCleanlinessRating());
        r.setAccuracyRating(req.getAccuracyRating());
        r.setCommunicationRating(req.getCommunicationRating());
        r.setLocationRating(req.getLocationRating());
        r.setValueRating(req.getValueRating());
        r.setCheckInRating(req.getCheckInRating());
        r.setReviewTitle(req.getReviewTitle());
        r.setReviewText(req.getReviewText());
        r.setCreatedAt(LocalDateTime.now());
        return r;
    }
}
