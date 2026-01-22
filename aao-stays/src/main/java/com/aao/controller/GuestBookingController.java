// package com.aao.controller;

// import com.aao.dto.BookingGuestDto;
// import com.aao.serviceInterface.BookingGuestService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/v1/booking-guests")
// @RequiredArgsConstructor
// public class BookingGuestController {

//     private final BookingGuestService bookingGuestService;
//     @PostMapping("/booking/{bookingId}")
//     @PreAuthorize("isAuthenticated()")
//     public ResponseEntity<BookingGuestDto> addGuest(
//             @PathVariable Long bookingId,
//             @RequestBody BookingGuestDto dto) {
//         System.out.println("🔥 ADD GUEST CONTROLLER HIT");
//         BookingGuestDto response = bookingGuestService.addGuestToBooking(bookingId, dto);

//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }

//     @GetMapping("/booking/{bookingId}")
//     public ResponseEntity<List<BookingGuestDto>> getGuests(
//             @PathVariable Long bookingId) {

//         return ResponseEntity.ok(
//                 bookingGuestService.getGuestsByBooking(bookingId));
//     }

//     @DeleteMapping("/{guestId}")
//     public ResponseEntity<Void> deleteGuest(
//             @PathVariable Long guestId) {

//         bookingGuestService.deleteGuest(guestId);
//         return ResponseEntity.noContent().build();
//     }
// }

package com.aao.controller;

import com.aao.dto.BookingGuestRequestDto;
import com.aao.serviceInterface.GuestBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class GuestBookingController {

    private final GuestBookingService guestBookingService;

    @PostMapping("/guest")
    public ResponseEntity<?> createGuestBooking(
            @RequestBody BookingGuestRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guestBookingService.createGuestBooking(dto));
    }
}
