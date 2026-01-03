package com.aao.serviceImpl;

import com.aao.dto.BookingCreateRequestDto;
import com.aao.dto.BookingResponseDto;
import com.aao.entity.Booking;
import com.aao.entity.BookingGuest;
import com.aao.repository.BookingRepository;
import com.aao.repository.BookingGuestRepository;
import com.aao.serviceInterface.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final BookingGuestRepository bookingGuestRepository;

    @Override
    public BookingResponseDto createBooking(BookingCreateRequestDto request) {
        Booking booking = Booking.builder()
                .propertyId(request.getPropertyId())
                .roomId(request.getRoomId())
                .guestId(request.getGuestId())
                .couponId(request.getCouponId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(request.getNumberOfGuests())
                .numberOfNights(request.getNumberOfNights())
                .basePrice(request.getBasePrice())
                .extraGuestCharges(request.getExtraGuestCharges())
                .discountAmount(request.getDiscountAmount())
                .totalAmount(request.getTotalAmount())
                .bookingStatus(request.getBookingStatus())
                .specialRequests(request.getSpecialRequests())
                .cancellationReason(request.getCancellationReason())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return mapToDto(savedBooking);
    }

    @Override
    public List<BookingResponseDto> getBookingsByGuest(Long guestId) {
        return bookingRepository.findByGuestId(guestId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto addGuestToBooking(Long bookingId, BookingGuest guest) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        guest.setBooking(booking);  // Associate guest with booking
        BookingGuest savedGuest = bookingGuestRepository.save(guest);

        return mapToDto(booking);  // Return updated booking DTO
    }

    private BookingResponseDto mapToDto(Booking booking) {
        return BookingResponseDto.builder()
                .bookingId(booking.getBookingId())
                .guestId(booking.getGuestId())
                .propertyId(booking.getPropertyId())
                .roomId(booking.getRoomId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .totalAmount(booking.getTotalAmount())
                .bookingStatus(booking.getBookingStatus())
                .build();
    }
}
