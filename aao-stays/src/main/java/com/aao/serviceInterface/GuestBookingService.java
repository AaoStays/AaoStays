package com.aao.serviceInterface;

import com.aao.dto.BookingGuestRequestDto;

public interface GuestBookingService {

    Object createGuestBooking(BookingGuestRequestDto dto);
}
