package com.aao.controller;

import com.aao.dto.BookingCreateRequestDto;
import com.aao.dto.BookingResponseDto;
import com.aao.entity.BookingGuest;
import com.aao.serviceInterface.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingCreateRequestDto request) {
        BookingResponseDto booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
    }

    // Get all bookings for a guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByGuest(@PathVariable Long guestId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByGuest(guestId);
        return ResponseEntity.ok(bookings);
    }

    // Add a guest to an existing booking
    @PostMapping("/{bookingId}/guests")
    public ResponseEntity<BookingResponseDto> addGuestToBooking(@PathVariable Long bookingId, 
                                                                 @RequestBody BookingGuest guest) {
        BookingResponseDto booking = bookingService.addGuestToBooking(bookingId, guest);
        return ResponseEntity.ok(booking);
    }
}
