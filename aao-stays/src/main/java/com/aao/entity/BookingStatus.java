package com.aao.entity;

/**
 * Enum representing the status of a booking.
 */
public enum BookingStatus {
    PENDING, // Initial state when booking is created
    CONFIRMED, // Booking confirmed after payment
    CHECKED_IN, // Guest has checked in
    CHECKED_OUT, // Guest has checked out
    CANCELLED, // Booking cancelled
    REFUNDED, // Refund processed
    NO_SHOW // Guest did not show up
}
