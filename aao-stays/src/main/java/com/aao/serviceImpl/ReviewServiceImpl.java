package com.aao.serviceImpl;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aao.dto.PropertyReviewResponse;
import com.aao.dto.ReviewRequest;
import com.aao.dto.ReviewResponse;
import com.aao.entity.Booking;
import com.aao.entity.Property;
import com.aao.entity.Review;
import com.aao.entity.User;
import com.aao.entity.UserType;
import com.aao.repo.BookingRepository;
import com.aao.repo.PropertyRepository;
import com.aao.repo.ReviewRepo;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.IReviewService;
import com.aao.utils.ReviewMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {

    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;
    private final PropertyRepository propertyRepo;
    private final BookingRepository bookingRepo;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return null;
        return userRepo.findByEmail(auth.getName()).orElse(null);
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null)
            return null;
        return auth.getName();
    }

    @Transactional
    public void updatePropertyRating(Long propertyId) {
        if (propertyId == null) {
            throw new IllegalArgumentException("Property ID must not be null");
        }

        Double avgRating = reviewRepo.calculateAverageRating(propertyId);
        if (avgRating == null) {
            avgRating = 0.0;
        }
        long totalReviews = reviewRepo.countVisibleReviewsByPropertyId(propertyId);

        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setRatingAverage(BigDecimal.valueOf(avgRating));
        property.setTotalReviews((int) totalReviews);

        propertyRepo.save(property);
    }

    @Override
    @Transactional
    public ApiResponse<ReviewResponse> addReview(ReviewRequest request, String currentUserEmail) {
        User user = getCurrentUser();
        if (user == null)
            return new ApiResponse<>(401, "Unauthorized", null);

        Long propertyId = request.getPropertyId();
        Long bookingId = request.getBookingId();

        if (propertyId == null || bookingId == null) {
            return new ApiResponse<>(400, "Property Id and Booking Id are required", null);
        }

        // ensure booking belongs to this user (optional)
        if (reviewRepo.existsByBookingId(bookingId)) {
            return new ApiResponse<>(400, "Review already exists for this booking", null);
        }

        // Validate property exists
        if (!propertyRepo.existsById(propertyId)) {
            return new ApiResponse<>(404, "Property not found", null);
        }

        // Validate booking exists and belongs to user
        Booking booking = bookingRepo.findById(bookingId)
                .orElse(null);

        if (booking == null) {
            return new ApiResponse<>(404, "Booking not found", null);
        }

        if (!booking.getUser().getId().equals(user.getId())) {
            return new ApiResponse<>(403, "You can only review your own bookings", null);
        }

        if (!booking.getProperty().getPropertyId().equals(propertyId)) {
            return new ApiResponse<>(400, "Booking does not match the specified property", null);
        }

        Review r = ReviewMapper.fromRequest(request);
        r.setGuestId(user.getId());
        r.setIsVisible(true);
        r.setIsVerified(false);
        r.setIsFeatured(false);
        r = reviewRepo.save(r);
        updatePropertyRating(propertyId);

        return new ApiResponse<>(200, "Review added successfully", ReviewMapper.toResponse(r));
    }

    @Override
    @Transactional
    public ApiResponse<ReviewResponse> updateReview(
            Long reviewId,
            ReviewRequest request,
            String ignoredEmail) {
        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        String email = getCurrentUserEmail();

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 🔐 OWNERSHIP CHECK (YEHI LINE TUM POOCH RAHE THE)
        Long guestId = review.getGuestId();
        String reviewOwnerEmail = (guestId == null) ? null
                : userRepo.findById(guestId)
                        .map(User::getEmail)
                        .orElse(null);

        if (!isAdmin && (reviewOwnerEmail == null || !reviewOwnerEmail.equals(email))) {
            return new ApiResponse<>(403, "You can update only your own review", null);
        }

        // update allowed fields
        review.setOverallRating(request.getOverallRating());
        review.setReviewTitle(request.getReviewTitle());
        review.setReviewText(request.getReviewText());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepo.save(review);
        updatePropertyRating(review.getPropertyId());

        return new ApiResponse<>(200, "Review updated", ReviewMapper.toResponse(review));
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteReview(Long reviewId, String ignored) {
        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        String email = getCurrentUserEmail();

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Long guestId = review.getGuestId();
        String reviewOwnerEmail = (guestId == null) ? null
                : userRepo.findById(guestId)
                        .map(User::getEmail)
                        .orElse(null);

        if (!isAdmin && (reviewOwnerEmail == null || !reviewOwnerEmail.equals(email))) {
            return new ApiResponse<>(403, "You can delete only your own review", null);
        }

        reviewRepo.delete(review);
        updatePropertyRating(review.getPropertyId());
        return new ApiResponse<>(200, "Review deleted", null);
    }

    @Override
    @Transactional
    public ApiResponse<ReviewResponse> hostReply(Long reviewId, String hostResponse, String currentUserEmail) {
        User user = getCurrentUser();
        if (user == null)
            return new ApiResponse<>(401, "Unauthorized", null);

        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }
        if (user.getUserType() != UserType.HOST && user.getUserType() != UserType.ADMIN) {
            throw new AccessDeniedException("Only host or admin can reply");
        }

        Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        // Optional: verify that this host owns the property of the review (you have
        // property->host mapping)
        r.setHostResponse(hostResponse);
        r.setHostResponseDate(LocalDateTime.now());
        reviewRepo.save(r);
        return new ApiResponse<>(200, "Host replied", ReviewMapper.toResponse(r));
    }

    @Override
    @Transactional
    public ApiResponse<String> featureReview(Long reviewId, boolean feature) {
        User user = getCurrentUser();
        if (user == null || user.getUserType() != UserType.ADMIN)
            return new ApiResponse<>(401, "Unauthorized", null);

        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }

        Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        r.setIsFeatured(feature);
        reviewRepo.save(r);
        return new ApiResponse<>(200, feature ? "Review featured" : "Review unfeatured", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> hideReview(Long reviewId, boolean hide) {
        User user = getCurrentUser();
        if (user == null || user.getUserType() != UserType.ADMIN)
            return new ApiResponse<>(401, "Unauthorized", null);

        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }

        Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        r.setIsVisible(!hide);
        reviewRepo.save(r);
        updatePropertyRating(r.getPropertyId());
        return new ApiResponse<>(200, hide ? "Review hidden" : "Review visible", null);
    }

    @Override
    public ApiResponse<PropertyReviewResponse> getReviewsByProperty(
            Long propertyId,
            boolean includeHidden) {
        List<Review> list = includeHidden
                ? reviewRepo.findByPropertyIdOrderByCreatedAtDesc(propertyId)
                : reviewRepo.findByPropertyIdAndIsVisibleTrueOrderByCreatedAtDesc(propertyId);

        Double avg = reviewRepo.calculateAverageRating(propertyId);
        if (avg == null)
            avg = 0.0;

        List<ReviewResponse> reviews = list.stream()
                .map(ReviewMapper::toResponse)
                .toList();

        PropertyReviewResponse response = new PropertyReviewResponse();
        response.setAverageRating(
                Math.round(avg * 10.0) / 10.0 // 1 decimal
        );
        response.setReviews((long) reviews.size());
        response.setTotalReviews(reviews);

        return new ApiResponse<>(200, "Reviews fetched", response);
    }

    @Override
    public ApiResponse<ReviewResponse> getReviewById(Long reviewId) {
        if (reviewId == null) {
            return new ApiResponse<>(400, "Review ID must not be null", null);
        }
        Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return new ApiResponse<>(200, "Found", ReviewMapper.toResponse(r));
    }
}
