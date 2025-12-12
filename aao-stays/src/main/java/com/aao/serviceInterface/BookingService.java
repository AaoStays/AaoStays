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

  
    ApiResponse<BookingDto> getBookingById(Long bookingId);

   
    ApiResponse<BookingDto> getBookingByReference(String bookingReference);

    
    ApiResponse<List<BookingDto>> getAllBookings(BookingStatus status, Long propertyId, Long userId);

   
    ApiResponse<BookingDto> updateBooking(Long bookingId, BookingRequestDto bookingRequestDto);


    ApiResponse<BookingDto> confirmBooking(Long bookingId, BookingConfirmDto confirmDto);

   
    ApiResponse<BookingDto> cancelBooking(Long bookingId, BookingCancelDto cancelDto, Long cancelledBy);

    
    ApiResponse<PriceBreakdownDto> calculatePrice(PriceCalculationRequestDto priceRequest);

    ApiResponse<Boolean> checkAvailability(Long propertyId, Long roomId, LocalDate checkIn, LocalDate checkOut);

   
    ApiResponse<List<BookingDto>> getUserBookings(Long userId);

    ApiResponse<BookingDto> processRefund(Long bookingId, RefundRequestDto refundRequest);
}
