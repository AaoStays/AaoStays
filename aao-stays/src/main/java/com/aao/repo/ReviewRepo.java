package com.aao.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aao.entity.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findByPropertyIdAndIsVisibleTrueOrderByCreatedAtDesc(Long propertyId);

    List<Review> findByPropertyIdOrderByCreatedAtDesc(Long propertyId);

    boolean existsByBookingId(Long bookingId);

    // ⭐ Average Rating (only visible reviews)
    // ⭐ Average Rating (only visible reviews)
    @Query("SELECT AVG(r.overallRating) FROM Review r WHERE r.propertyId = :propertyId AND r.isVisible = true")
    Double calculateAverageRating(Long propertyId);

    // ⭐ Total Review Count
    @Query("SELECT COUNT(r) FROM Review r WHERE r.propertyId = :propertyId AND r.isVisible = true")
    Long countVisibleReviewsByPropertyId(Long propertyId);
}
