package com.aao.serviceInterface;

import com.aao.dto.BookingGuestDto;
import java.util.List;

public interface BookingGuestService {

    BookingGuestDto addGuestToBooking(Long bookingId, BookingGuestDto dto);

    List<BookingGuestDto> getGuestsByBooking(Long bookingId);

    void deleteGuest(Long bookingGuestId);
}
