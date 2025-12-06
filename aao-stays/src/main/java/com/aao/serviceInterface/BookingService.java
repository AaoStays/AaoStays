package com.aao.serviceInterface;

import com.aao.dto.*;
import com.aao.entity.BookingStatus;
import com.aao.response.ApiResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for Booking management operations.
 */
public interface BookingService {

    /**
     * Creates a new booking with automatic price calculation.
     */
    ApiResponse<BookingDto> createBooking(BookingRequestDto bookingRequestDto);

    /**
     * Retrieves booking details by booking ID.
     */
    ApiResponse<BookingDto> getBookingById(Long bookingId);

    /**
     * Retrieves booking details by booking reference.
     */
    ApiResponse<BookingDto> getBookingByReference(String bookingReference);

    /**
     * Retrieves all bookings with optional filters.
     */
    ApiResponse<List<BookingDto>> getAllBookings(BookingStatus status, Long propertyId, Long userId);

    /**
     * Updates an existing booking.
     */
    ApiResponse<BookingDto> updateBooking(Long bookingId, BookingRequestDto bookingRequestDto);

    /**
     * Confirms a booking after payment.
     */
    ApiResponse<BookingDto> confirmBooking(Long bookingId, BookingConfirmDto confirmDto);

    /**
     * Cancels a booking.
     */
    ApiResponse<BookingDto> cancelBooking(Long bookingId, BookingCancelDto cancelDto, Long cancelledBy);

    /**
     * Calculates price breakdown for a booking request.
     */
    ApiResponse<PriceBreakdownDto> calculatePrice(PriceCalculationRequestDto priceRequest);

    /**
     * Checks availability for a property/room on given dates.
     */
    ApiResponse<Boolean> checkAvailability(Long propertyId, Long roomId, LocalDate checkIn, LocalDate checkOut);

    /**
     * Retrieves all bookings for a specific user.
     */
    ApiResponse<List<BookingDto>> getUserBookings(Long userId);

    /**
     * Processes a refund for a cancelled booking.
     */
    ApiResponse<BookingDto> processRefund(Long bookingId, RefundRequestDto refundRequest);
}
