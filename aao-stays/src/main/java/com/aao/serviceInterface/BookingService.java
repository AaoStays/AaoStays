package com.aao.serviceInterface;

import com.aao.dto.BookingCreateRequestDto;
import com.aao.dto.BookingResponseDto;

import java.util.List;

public interface IBookingService {

    BookingResponseDto createBooking(BookingCreateRequestDto request);
    List<BookingResponseDto> getBookingsByGuest(Long guestId);
    BookingResponseDto addGuestToBooking(Long bookingId, BookingGuest guest);
}
