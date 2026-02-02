package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.aao.entity.Booking;
import com.aao.entity.BookingStatus;
import com.aao.entity.PaymentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find by booking reference
    Optional<Booking> findByBookingReference(String bookingReference);

    // Find all bookings for a user
    List<Booking> findByUser_Id(Long userId);

    // Find all bookings for a property
    List<Booking> findByProperty_PropertyId(Long propertyId);

    // Find all bookings for a room
    List<Booking> findByRoom_RoomId(Long roomId);

    // Filter by booking status
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);

    // Filter by payment status
    List<Booking> findByPaymentStatus(PaymentStatus paymentStatus);

    // Combined filters
    List<Booking> findByBookingStatusAndPaymentStatus(BookingStatus bookingStatus, PaymentStatus paymentStatus);

    List<Booking> findByUser_IdAndBookingStatus(Long userId, BookingStatus bookingStatus);

    List<Booking> findByProperty_PropertyIdAndBookingStatus(Long propertyId, BookingStatus bookingStatus);

    // Find bookings within date range
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

    List<Booking> findByCheckOutDateBetween(LocalDate startDate, LocalDate endDate);

    // Check for overlapping bookings (for availability)
    @Query("SELECT b FROM Booking b WHERE b.property.propertyId = :propertyId " +
            "AND b.bookingStatus NOT IN ('CANCELLED', 'REFUNDED', 'NO_SHOW') " +
            "AND ((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    List<Booking> findOverlappingBookingsForProperty(
            @Param("propertyId") Long propertyId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b WHERE b.room.roomId = :roomId " +
            "AND b.bookingStatus NOT IN ('CANCELLED', 'REFUNDED', 'NO_SHOW') " +
            "AND ((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    List<Booking> findOverlappingBookingsForRoom(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

    // Find bookings by user and date range
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId " +
            "AND b.checkInDate >= :startDate AND b.checkOutDate <= :endDate")
    List<Booking> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Count bookings by status
    Long countByBookingStatus(BookingStatus bookingStatus);

    Long countByProperty_PropertyIdAndBookingStatus(Long propertyId, BookingStatus bookingStatus);
}
