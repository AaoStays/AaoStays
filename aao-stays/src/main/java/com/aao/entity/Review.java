package com.aao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, unique = true)
    private Long bookingId;

    @Column(nullable = false)
    private Long propertyId;

    @Column(nullable = false)
    private Long guestId; // reference to users.id

    @Column(precision = 3, scale = 2, nullable = false)
    private BigDecimal overallRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal cleanlinessRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal accuracyRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal communicationRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal locationRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal valueRating;

    @Column(precision = 3, scale = 2)
    private BigDecimal checkInRating;

    private String reviewTitle;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    @Column(columnDefinition = "TEXT")
    private String hostResponse;

    private LocalDateTime hostResponseDate;

    @Builder.Default
    private Boolean isVerified = false;

    @Builder.Default
    private Boolean isVisible = true;

    @Builder.Default
    private Boolean isFeatured = false;

    @Builder.Default
    private Integer helpfulCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

   
}