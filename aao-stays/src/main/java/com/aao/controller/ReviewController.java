package com.aao.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import com.aao.dto.ReviewRequest;
import com.aao.dto.ReviewResponse;
import com.aao.dto.PropertyReviewResponse;
import com.aao.response.ApiResponse;
import com.aao.service.IReviewService;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

        private final IReviewService reviewService;

        // 🔹 ADD REVIEW
        @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
        @PostMapping("/add")
        public ResponseEntity<ApiResponse<ReviewResponse>> addReview(
                        @RequestBody ReviewRequest request) {

                String email = SecurityContextHolder.getContext()
                                .getAuthentication().getName();

                ApiResponse<ReviewResponse> response = reviewService.addReview(request, email);

                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // 🔹 UPDATE REVIEW (own only)
        @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ReviewResponse>> update(
                        @PathVariable Long id,
                        @RequestBody ReviewRequest request) {

                String email = SecurityContextHolder.getContext()
                                .getAuthentication().getName();

                ApiResponse<ReviewResponse> response = reviewService.updateReview(id, request, email);

                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // 🔹 DELETE REVIEW
        @PreAuthorize("hasAnyRole('GUEST','ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

                String email = SecurityContextHolder.getContext()
                                .getAuthentication().getName();

                ApiResponse<String> response = reviewService.deleteReview(id, email);

                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // 🔹 HOST REPLY
        @PreAuthorize("hasAnyRole('HOST','ADMIN')")
        @PostMapping("/{id}/reply")
        public ResponseEntity<ApiResponse<ReviewResponse>> reply(
                        @PathVariable Long id,
                        @RequestBody String hostResponse) {

                String email = SecurityContextHolder.getContext()
                                .getAuthentication().getName();

                ApiResponse<ReviewResponse> response = reviewService.hostReply(id, hostResponse, email);

                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // 🔹 PUBLIC VIEW
        @GetMapping("/property/{propertyId}")
        public ResponseEntity<ApiResponse<PropertyReviewResponse>> getByProperty(
                        @PathVariable Long propertyId) {

                ApiResponse<PropertyReviewResponse> response = reviewService.getReviewsByProperty(propertyId, false);

                return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        // ================= ADMIN FEATURE =================
        // @PreAuthorize("hasRole('ADMIN')")
        // @PutMapping("/{id}/feature")
        // public ResponseEntity<ApiResponse<String>> feature(
        // @PathVariable Long id,
        // @RequestParam boolean feature) {
        // return ResponseEntity.ok(reviewService.featureReview(id, feature));
        // }

        // // ================= ADMIN HIDE =================
        // @PreAuthorize("hasRole('ADMIN')")
        // @PutMapping("/{id}/hide")
        // public ResponseEntity<ApiResponse<String>> hide(
        // @PathVariable Long id,
        // @RequestParam boolean hide) {
        // return ResponseEntity.ok(reviewService.hideReview(id, hide));
        // }

        // // ================= PUBLIC =================
        // @GetMapping("/property/{propertyId}")
        // public ResponseEntity<ApiResponse<List<ReviewResponse>>> getByProperty(
        // @PathVariable Long propertyId) {
        // return ResponseEntity.ok(
        // reviewService.getReviewsByProperty(propertyId, false));
        // }

        // // ================= ADMIN VIEW ALL =================
        // @PreAuthorize("hasRole('ADMIN')")
        // @GetMapping("/property/{propertyId}/all")
        // public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllByProperty(
        // @PathVariable Long propertyId) {

        // ApiResponse<List<ReviewResponse>> response =
        // reviewService.getReviewsByProperty(propertyId, true);

        // return ResponseEntity.status(response.getStatusCode()).body(response);
        // }

        // // ================= GET SINGLE =================
        // @GetMapping("/{id}")
        // public ResponseEntity<ApiResponse<ReviewResponse>> getById(@PathVariable Long
        // id) {
        // ApiResponse<ReviewResponse> response = reviewService.getReviewById(id);

        // return ResponseEntity.status(response.getStatusCode()).body(response);
        // }

}
