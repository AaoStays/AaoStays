package com.aao.repo;

import com.aao.entity.BookingGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingGuestRepository
        extends JpaRepository<BookingGuest, Long> {

    List<BookingGuest> findByBooking_BookingId(Long bookingId);
}
