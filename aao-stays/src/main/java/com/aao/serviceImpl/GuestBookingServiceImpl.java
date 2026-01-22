// package com.aao.serviceImpl;

// import com.aao.dto.BookingGuestDto;
// import com.aao.entity.Booking;
// import com.aao.entity.BookingGuest;
// import com.aao.repo.BookingGuestRepository;
// import com.aao.repo.BookingRepository;
// import com.aao.serviceInterface.BookingGuestService;
// import lombok.RequiredArgsConstructor;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// @RequiredArgsConstructor
// public class BookingGuestServiceImpl implements BookingGuestService {

//     private final BookingRepository bookingRepository;
//     private final BookingGuestRepository bookingGuestRepository;

//     private String getCurrentUserEmail() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth == null || auth.getName() == null)
//             return null;
//         return auth.getName();
//     }

//     @Override
//     public BookingGuestDto addGuestToBooking(Long bookingId, BookingGuestDto dto) {

//         String currentUserEmail = getCurrentUserEmail();

//         Booking booking = bookingRepository.findById(bookingId)
//                 .orElseThrow(() ->
//                         new RuntimeException("Booking not found with id: " + bookingId));

//         // 🔐 OWNERSHIP CHECK
//         if (!booking.getUser().getEmail().equals(currentUserEmail)) {
//             throw new RuntimeException("You are not allowed to add guest to this booking");
//         }

//         BookingGuest guest = BookingGuest.builder()
//                 .booking(booking)
//                 .guestFullName(dto.getGuestFullName())
//                 .guestEmail(dto.getGuestEmail())
//                 .guestPhone(dto.getGuestPhone())
//                 .guestAge(dto.getGuestAge())
//                 .guestIdProofType(dto.getGuestIdProofType())
//                 .guestIdProofNumber(dto.getGuestIdProofNumber())
//                 .relationshipToBooker(dto.getRelationshipToBooker())
//                 .build();

//         BookingGuest saved = bookingGuestRepository.save(guest);

//         return BookingGuestDto.builder()
//                 .bookingGuestId(saved.getBookingGuestId())
//                 .bookingId(bookingId)
//                 .guestFullName(saved.getGuestFullName())
//                 .guestEmail(saved.getGuestEmail())
//                 .guestPhone(saved.getGuestPhone())
//                 .guestAge(saved.getGuestAge())
//                 .guestIdProofType(saved.getGuestIdProofType())
//                 .guestIdProofNumber(saved.getGuestIdProofNumber())
//                 .relationshipToBooker(saved.getRelationshipToBooker())
//                 .build();
//     }

//     @Override
//     public List<BookingGuestDto> getGuestsByBooking(Long bookingId) {

//         String currentUserEmail = getCurrentUserEmail();

//         Booking booking = bookingRepository.findById(bookingId)
//                 .orElseThrow(() ->
//                         new RuntimeException("Booking not found with id: " + bookingId));

//         if (!booking.getUser().getEmail().equals(currentUserEmail)) {
//             throw new RuntimeException("You are not allowed to view guests of this booking");
//         }

//         return bookingGuestRepository.findByBooking_BookingId(bookingId)
//                 .stream()
//                 .map(g -> BookingGuestDto.builder()
//                         .bookingGuestId(g.getBookingGuestId())
//                         .bookingId(bookingId)
//                         .guestFullName(g.getGuestFullName())
//                         .guestEmail(g.getGuestEmail())
//                         .guestPhone(g.getGuestPhone())
//                         .guestAge(g.getGuestAge())
//                         .guestIdProofType(g.getGuestIdProofType())
//                         .guestIdProofNumber(g.getGuestIdProofNumber())
//                         .relationshipToBooker(g.getRelationshipToBooker())
//                         .build())
//                 .collect(Collectors.toList());
//     }

//     @Override
//     public void deleteGuest(Long guestId) {

//         String currentUserEmail = getCurrentUserEmail();

//         BookingGuest guest = bookingGuestRepository.findById(guestId)
//                 .orElseThrow(() ->
//                         new RuntimeException("Guest not found with id: " + guestId));

//         Booking booking = guest.getBooking();

//         if (!booking.getUser().getEmail().equals(currentUserEmail)) {
//             throw new RuntimeException("You are not allowed to delete this guest");
//         }

//         bookingGuestRepository.deleteById(guestId);
//     }
// }

package com.aao.serviceImpl;

import com.aao.dto.BookingGuestRequestDto;
import com.aao.entity.Booking;
import com.aao.entity.BookingGuest;
import com.aao.entity.Property;
import com.aao.entity.BookingStatus;
import com.aao.entity.PaymentStatus;
import com.aao.repo.BookingGuestRepository;
import com.aao.repo.BookingRepository;
import com.aao.repo.PropertyRepository;
import com.aao.serviceInterface.GuestBookingService;
import com.aao.utils.BookingReferenceGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class GuestBookingServiceImpl implements GuestBookingService {

        private final BookingReferenceGenerator bookingReferenceGenerator;
        private final BookingRepository bookingRepository;
        private final BookingGuestRepository bookingGuestRepository;
        private final PropertyRepository propertyRepository;

        @Override
        @Transactional
        @SuppressWarnings("null")
        public Booking createGuestBooking(BookingGuestRequestDto dto) {

                // 🔹 1. Validate basic request
                Long propertyId = dto.getPropertyId();
                if (propertyId == null) {
                        throw new IllegalArgumentException("Property ID is required");
                }
                if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
                        throw new IllegalArgumentException("Check-in and check-out dates are required");
                }
                if (dto.getGuestFullName() == null || dto.getGuestPhone() == null) {
                        throw new IllegalArgumentException("Guest name and phone are required");
                }

                // 🔹 2. Fetch property
                Property property = propertyRepository.findById(propertyId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Property not found with ID: " + propertyId));

                // 🔹 3. Calculate nights
                long nights = ChronoUnit.DAYS.between(
                                dto.getCheckInDate(),
                                dto.getCheckOutDate());

                if (nights < 1) {
                        throw new IllegalArgumentException("Booking must be at least 1 night");
                }

                // 🔹 4. Basic price calculation (MANDATORY FOR DB)
                BigDecimal pricePerNight = property.getPricePerNight();
                BigDecimal basePrice = pricePerNight.multiply(BigDecimal.valueOf(nights));

                // 🔹 5. Create Booking entity
                Booking booking = new Booking();

                booking.setBookingReference(bookingReferenceGenerator.generateReference());
                booking.setProperty(property);
                booking.setCheckInDate(dto.getCheckInDate());
                booking.setCheckOutDate(dto.getCheckOutDate());
                booking.setNumberOfNights((int) nights);

                booking.setNumberOfGuests(
                                dto.getNumberOfGuests() != null ? dto.getNumberOfGuests() : 1);
                booking.setNumberOfAdults(1);
                booking.setNumberOfChildren(0);
                booking.setNumberOfInfants(0);

                // 🔹 REQUIRED pricing fields (NOT NULL)
                booking.setBasePrice(basePrice);
                booking.setExtraGuestCharges(BigDecimal.ZERO);
                booking.setCleaningFee(BigDecimal.ZERO);
                booking.setServiceFee(BigDecimal.ZERO);
                booking.setDiscountAmount(BigDecimal.ZERO);
                booking.setTaxAmount(BigDecimal.ZERO);
                booking.setTotalAmount(basePrice);

                booking.setBookingStatus(BookingStatus.PENDING);
                booking.setPaymentStatus(PaymentStatus.PENDING);
                booking.setCreatedAt(LocalDateTime.now());
                booking.setUpdatedAt(LocalDateTime.now());

                // 🔹 IMPORTANT: guest booking → no user
                booking.setUser(null);

                // 🔹 6. Save booking
                Booking savedBooking = bookingRepository.save(booking);

                // 🔹 7. Create BookingGuest entity
                BookingGuest guest = BookingGuest.builder()
                                .booking(savedBooking)
                                .guestFullName(dto.getGuestFullName())
                                .guestEmail(dto.getGuestEmail())
                                .guestPhone(dto.getGuestPhone())
                                .guestAge(dto.getGuestAge())
                                .guestIdProofType(dto.getGuestIdProofType())
                                .guestIdProofNumber(dto.getGuestIdProofNumber())
                                .relationshipToBooker("SELF")
                                .build();

                bookingGuestRepository.save(guest);

                // 🔹 8. Return booking
                return savedBooking;
        }
}
