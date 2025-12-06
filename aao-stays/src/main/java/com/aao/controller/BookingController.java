package com.aao.controller;

import com.aao.dto.*;
import com.aao.entity.BookingStatus;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Booking Management API.
 * Handles all booking-related operations.
 */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // 1. Create Booking - Authenticated users
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> createBooking(
            @RequestBody BookingRequestDto bookingRequestDto) {
        ApiResponse<BookingDto> response = bookingService.createBooking(bookingRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 2. Get Booking by ID - Authenticated users (own bookings) or Admin
    @GetMapping("/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> getBookingById(@PathVariable Long bookingId) {
        ApiResponse<BookingDto> response = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 3. Get Booking by Reference - Authenticated users
    @GetMapping("/reference/{bookingReference}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> getBookingByReference(
            @PathVariable String bookingReference) {
        ApiResponse<BookingDto> response = bookingService.getBookingByReference(bookingReference);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 4. List All Bookings with Filters - Admin only
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<BookingDto>>> getAllBookings(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) Long userId) {
        ApiResponse<List<BookingDto>> response = bookingService.getAllBookings(status, propertyId, userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 5. Update Booking - Authenticated users (own bookings) or Admin
    @PutMapping("/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingRequestDto bookingRequestDto) {
        ApiResponse<BookingDto> response = bookingService.updateBooking(bookingId, bookingRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 6. Confirm Booking - Authenticated users or Admin
    @PostMapping("/{bookingId}/confirm")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> confirmBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingConfirmDto confirmDto) {
        ApiResponse<BookingDto> response = bookingService.confirmBooking(bookingId, confirmDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 7. Cancel Booking - Authenticated users (own bookings) or Admin
    @PostMapping("/{bookingId}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BookingDto>> cancelBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingCancelDto cancelDto,
            @RequestParam(required = false) Long cancelledBy) {
        ApiResponse<BookingDto> response = bookingService.cancelBooking(bookingId, cancelDto, cancelledBy);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 8. Calculate Price - Public endpoint
    @PostMapping("/calculate-price")
    public ResponseEntity<ApiResponse<PriceBreakdownDto>> calculatePrice(
            @RequestBody PriceCalculationRequestDto priceRequest) {
        ApiResponse<PriceBreakdownDto> response = bookingService.calculatePrice(priceRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 9. Check Property Availability - Public endpoint
    @GetMapping("/properties/{propertyId}/availability")
    public ResponseEntity<ApiResponse<Boolean>> checkPropertyAvailability(
            @PathVariable Long propertyId,
            @RequestParam(required = false) Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        ApiResponse<Boolean> response = bookingService.checkAvailability(propertyId, roomId, checkIn, checkOut);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 10. Get User Bookings - Authenticated users (own) or Admin
    @GetMapping("/users/{userId}/bookings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<BookingDto>>> getUserBookings(@PathVariable Long userId) {
        ApiResponse<List<BookingDto>> response = bookingService.getUserBookings(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 11. Process Refund - Admin only
    @PostMapping("/{bookingId}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookingDto>> processRefund(
            @PathVariable Long bookingId,
            @RequestBody RefundRequestDto refundRequest) {
        ApiResponse<BookingDto> response = bookingService.processRefund(bookingId, refundRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
