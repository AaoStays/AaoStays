package com.aao.entity;


public enum BookingStatus {
    PENDING, 
    CONFIRMED, // Booking confirmed after payment
    CHECKED_IN, // Guest has checked in
    CHECKED_OUT, // Guest has checked out
    CANCELLED, // Booking cancelled
    REFUNDED, // Refund processed
    NO_SHOW // Guest did not show up
, ACCEPTED
}
