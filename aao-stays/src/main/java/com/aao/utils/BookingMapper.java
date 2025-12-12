package com.aao.utils;

import com.aao.dto.BookingDto;
import com.aao.entity.Booking;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

/**
 * Mapper utility for converting between Booking entities and BookingDto
 * objects.
 */
@Component
public class BookingMapper {

    /**
     * Converts a Booking entity to BookingDto for response.
     *
     * @param booking The booking entity to convert
     * @return BookingDto with all booking information
     */
    public BookingDto toDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto dto = new BookingDto();

        // Basic booking information
        dto.setBookingId(booking.getBookingId());
        dto.setBookingReference(booking.getBookingReference());
        dto.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        // Property and Room information
        dto.setPropertyId(booking.getPropertyId());
        if (booking.getProperty() != null) {
            dto.setPropertyName(booking.getProperty().getPropertyName());
        }

        dto.setRoomId(booking.getRoomId());
        if (booking.getRoom() != null) {
            dto.setRoomName(booking.getRoom().getRoomName());
        }

        // User information
        dto.setUserId(booking.getUserId());
        if (booking.getUser() != null) {
            dto.setGuestName(booking.getUser().getFullName());
            dto.setGuestEmail(booking.getUser().getEmail());
        }

        // Booking dates
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setNumberOfNights(booking.getNumberOfNights());

        // Guest counts
        dto.setNumberOfGuests(booking.getNumberOfGuests());
        dto.setNumberOfAdults(booking.getNumberOfAdults());
        dto.setNumberOfChildren(booking.getNumberOfChildren());
        dto.setNumberOfInfants(booking.getNumberOfInfants());

        // Pricing breakdown
        dto.setBasePrice(booking.getBasePrice());
        dto.setExtraGuestCharges(booking.getExtraGuestCharges());
        dto.setCleaningFee(booking.getCleaningFee());
        dto.setServiceFee(booking.getServiceFee());
        dto.setDiscountAmount(booking.getDiscountAmount());
        dto.setTaxAmount(booking.getTaxAmount());
        dto.setTotalAmount(booking.getTotalAmount());

        // Status
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setPaymentStatus(booking.getPaymentStatus());

        // Additional info
        dto.setSpecialRequests(booking.getSpecialRequests());
        dto.setCancellationReason(booking.getCancellationReason());
        dto.setRefundAmount(booking.getRefundAmount());

        // Timestamps
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        dto.setConfirmedAt(booking.getConfirmedAt());
        dto.setCheckedInAt(booking.getCheckedInAt());
        dto.setCheckedOutAt(booking.getCheckedOutAt());
        dto.setCancelledAt(booking.getCancelledAt());
        dto.setRefundProcessedAt(booking.getRefundProcessedAt());

        return dto;
    }

    /**
     * Calculates the number of nights between check-in and check-out dates.
     *
     * @param checkIn  Check-in date
     * @param checkOut Check-out date
     * @return Number of nights
     */
    public int calculateNumberOfNights(java.time.LocalDate checkIn, java.time.LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
