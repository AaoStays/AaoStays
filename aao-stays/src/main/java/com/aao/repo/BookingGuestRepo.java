package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aao.entity.Booking;
import com.aao.entity.BookingGuest;

import java.util.List;

public interface BookingGuestRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingBookingId(Long bookingId);

	BookingGuest save(BookingGuest guest);
}
