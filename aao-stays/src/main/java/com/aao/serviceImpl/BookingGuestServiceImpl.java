package com.aao.serviceImpl;

import com.aao.dto.BookingGuestDto;
import com.aao.entity.Booking;
import com.aao.entity.BookingGuest;
import com.aao.repo.BookingGuestRepository;
import com.aao.repo.BookingRepository;
import com.aao.serviceInterface.BookingGuestService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class BookingGuestServiceImpl<bookingGuestRepository> implements BookingGuestService {

    private final BookingRepository bookingRepository;
    private final BookingGuestRepository bookingGuestRepository;

    public BookingGuestDto addGuestToBooking(Long bookingId, BookingGuestDto dto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingGuest guest = BookingGuest.builder()
                .booking(booking)
                .guestFullName(dto.getGuestFullName())
                .guestEmail(dto.getGuestEmail())
                .guestPhone(dto.getGuestPhone())
                .guestAge(dto.getGuestAge())
                .guestIdProofType(dto.getGuestIdProofType())
                .guestIdProofNumber(dto.getGuestIdProofNumber())
                .relationshipToBooker(dto.getRelationshipToBooker())
                .build();

        BookingGuest saved = bookingGuestRepository.save(guest);

        dto.setBookingGuestId(saved.getBookingGuestId());
        return dto;
    }

    public List<BookingGuestDto> getGuestsByBooking(Long bookingId) {
        return bookingGuestRepository.findByBookingBookingId(bookingId)
                .stream()
                .map(g -> new BookingGuestDto(
                        g.getBookingGuestId(),
                        bookingId,
                        g.getGuestFullName(),
                        g.getGuestEmail(),
                        g.getGuestPhone(),
                        g.getGuestAge(),
                        g.getGuestIdProofType(),
                        g.getGuestIdProofNumber(),
                        g.getRelationshipToBooker()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGuest(Long guestId) {

        if (!bookingGuestRepository.existsById(guestId)) {
            throw new RuntimeException("Booking guest not found with id: " + guestId);
        }

        bookingGuestRepository.deleteById(guestId);
    }


	
}
