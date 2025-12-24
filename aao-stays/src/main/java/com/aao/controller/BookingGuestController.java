package com.aao.controller;

import com.aao.dto.BookingGuestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingGuestController {

    private final com.aao.serviceInterface.BookingGuestService bookingGuestService;

    @PostMapping("/{bookingId}/guests")
    public ResponseEntity<BookingGuestDto> addGuest(
            @PathVariable Long bookingId,
            @RequestBody BookingGuestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingGuestService.addGuestToBooking(bookingId, dto));
    }

    @GetMapping("/{bookingId}/guests")
    public ResponseEntity<List<BookingGuestDto>> getGuests(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingGuestService.getGuestsByBooking(bookingId)
        );
    }

    @DeleteMapping("/guests/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {

        bookingGuestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }
}
