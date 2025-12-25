package com.aao.entity;

import com.aao.dto.BookingGuestDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_guest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingGuestId;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private String guestFullName;
    private String guestEmail;
    private String guestPhone;
    private Integer guestAge;

    private String guestIdProofType; 
    private String guestIdProofNumber;
    private String relationshipToBooker;
	public BookingGuestDto addGuestToBooking1(Long bookingId, BookingGuestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getGuestsByBooking(Long bookingId) {
		// TODO Auto-generated method stub
		return null;
	}
	public void deleteGuest1(Long guestId) {
		// TODO Auto-generated method stub
		
	}
	public BookingGuestDto addGuestToBooking(Long bookingId, BookingGuestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	public void deleteGuest(Long guestId) {
		// TODO Auto-generated method stub
		
	}
}
