package com.aao.serviceImpl;

import com.aao.dto.*;
import com.aao.entity.*;
import com.aao.repo.BookingRepository;
import com.aao.repo.HostRepo;
import com.aao.repo.PropertyRepository;
import com.aao.repo.RoomRepository;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.service.EmailService;
import com.aao.serviceInterface.BookingService;
import com.aao.utils.BookingMapper;
import com.aao.utils.BookingReferenceGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final UserRepo userRepo;
    private final BookingMapper bookingMapper;
    private final BookingReferenceGenerator bookingReferenceGenerator;
    private final HostRepo hostRepo;
    
    private final EmailService emailService;

    private static final BigDecimal TAX_RATE = new BigDecimal("0.12"); // 12% tax
    
    
    
    
    private String generateConfirmationCode() {
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "CONF-" + random;
    }


    @Override
    @Transactional
    public ApiResponse<BookingDto> createBooking(BookingRequestDto bookingRequestDto) {

        if (bookingRequestDto.getPropertyId() == null) {
            return new ApiResponse<>(400, "Property ID is required", null);
        }
        if (bookingRequestDto.getUserId() == null) {
            return new ApiResponse<>(400, "User ID is required", null);
        }
        if (bookingRequestDto.getCheckInDate() == null || bookingRequestDto.getCheckOutDate() == null) {
            return new ApiResponse<>(400, "Check-in and check-out dates are required", null);
        }
        if (bookingRequestDto.getCheckInDate().isBefore(LocalDate.now())) {
            return new ApiResponse<>(400, "Check-in date cannot be in the past", null);
        }
        if (bookingRequestDto.getCheckOutDate().isBefore(bookingRequestDto.getCheckInDate())) {
            return new ApiResponse<>(400, "Check-out date must be after check-in date", null);
        }

        Property property = propertyRepository.findById(bookingRequestDto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + bookingRequestDto.getPropertyId()));

        Room room = null;
        if (bookingRequestDto.getRoomId() != null) {
            room = roomRepository.findById(bookingRequestDto.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + bookingRequestDto.getRoomId()));
        }

        User user = userRepo.findById(bookingRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + bookingRequestDto.getUserId()));

        ApiResponse<Boolean> availabilityCheck = checkAvailability(
                bookingRequestDto.getPropertyId(),
                bookingRequestDto.getRoomId(),
                bookingRequestDto.getCheckInDate(),
                bookingRequestDto.getCheckOutDate()
        );

        if (!availabilityCheck.getData()) {
            return new ApiResponse<>(409, "Property/Room not available for selected dates", null);
        }  
        
        
 
        PriceCalculationRequestDto priceRequest = PriceCalculationRequestDto.builder()
                .propertyId(bookingRequestDto.getPropertyId())
                .roomId(bookingRequestDto.getRoomId())
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .numberOfGuests(bookingRequestDto.getNumberOfGuests())
                .couponCode(bookingRequestDto.getCouponCode())
                .build();
        
        

        ApiResponse<PriceBreakdownDto> priceResponse = calculatePrice(priceRequest);
        PriceBreakdownDto priceBreakdown = priceResponse.getData();
        String confirmationCode = generateConfirmationCode();

        Booking booking = new Booking();
        booking.setBookingReference(bookingReferenceGenerator.generateReference());
        booking.setBookingConfirmationCode(confirmationCode);
        booking.setProperty(property);
        booking.setRoom(room);
        booking.setUser(user);
        booking.setPropertyId(property.getPropertyId());
        booking.setRoomId(room != null ? room.getRoomId() : null);
        booking.setUserId(user.getId());
        booking.setCouponId(bookingRequestDto.getCouponId());
        booking.setCheckInDate(bookingRequestDto.getCheckInDate());
        booking.setCheckOutDate(bookingRequestDto.getCheckOutDate());
        booking.setNumberOfNights(priceBreakdown.getNumberOfNights());
        booking.setNumberOfGuests(bookingRequestDto.getNumberOfGuests());
        booking.setNumberOfAdults(bookingRequestDto.getNumberOfAdults() != null ? bookingRequestDto.getNumberOfAdults() : 1);
        booking.setNumberOfChildren(bookingRequestDto.getNumberOfChildren() != null ? bookingRequestDto.getNumberOfChildren() : 0);
        booking.setNumberOfInfants(bookingRequestDto.getNumberOfInfants() != null ? bookingRequestDto.getNumberOfInfants() : 0);
        booking.setBasePrice(priceBreakdown.getBasePrice());
        booking.setExtraGuestCharges(priceBreakdown.getExtraGuestCharges());
        booking.setCleaningFee(priceBreakdown.getCleaningFee());
        booking.setServiceFee(priceBreakdown.getServiceFee());
        booking.setDiscountAmount(priceBreakdown.getDiscountAmount());
        booking.setTaxAmount(priceBreakdown.getTaxAmount());
        booking.setTotalAmount(priceBreakdown.getTotalAmount());
        booking.setSpecialRequests(bookingRequestDto.getSpecialRequests());
        booking.setBookingStatus(BookingStatus.ACCEPTED);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);
        
        
        Host host = property.getHost();
        
        host.setTotalBookings(host.getTotalBookings()+1);
        hostRepo.save(host);
        
 
        host.setTotalEarnings(
                host.getTotalEarnings().add(savedBooking.getTotalAmount())
        );

        YearMonth currentMonth = YearMonth.now();
        YearMonth bookingMonth = YearMonth.from(savedBooking.getCheckInDate());

        if (currentMonth.equals(bookingMonth)) {
            host.setEarningsPerMonth(
                    host.getEarningsPerMonth().add(savedBooking.getTotalAmount())
            );
        }

   
    emailService.sendBookingEmail(
             savedBooking.getUser().getEmail(),
             savedBooking.getUser().getFullName(),
             savedBooking.getBookingReference(),
             savedBooking.getBookingConfirmationCode(),
             savedBooking.getProperty().getPropertyName(),
             savedBooking.getCheckInDate(),
             savedBooking.getCheckOutDate(),
             savedBooking.getTotalAmount()
     );

        
        return new ApiResponse<>(201, "Booking created successfully", bookingMapper.toDto(savedBooking));
    }

    @Override
    public ApiResponse<BookingDto> getBookingById(Long bookingId) {
        if (bookingId == null) {
            return new ApiResponse<>(400, "Booking ID is required", null);
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        return new ApiResponse<>(200, "Booking retrieved successfully", bookingMapper.toDto(booking));
    }

    @Override
    public ApiResponse<BookingDto> getBookingByReference(String bookingReference) {
        if (bookingReference == null || bookingReference.isEmpty()) {
            return new ApiResponse<>(400, "Booking reference is required", null);
        }

        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking not found with reference: " + bookingReference));

        return new ApiResponse<>(200, "Booking retrieved successfully", bookingMapper.toDto(booking));
    }

    @Override
    public ApiResponse<List<BookingDto>> getAllBookings(BookingStatus status, Long propertyId, Long userId) {
        List<Booking> bookings;

        if (status != null && propertyId != null) {
            bookings = bookingRepository.findByProperty_PropertyIdAndBookingStatus(propertyId, status);
        } else if (status != null && userId != null) {
            bookings = bookingRepository.findByUser_IdAndBookingStatus(userId, status);
        } else if (status != null) {
            bookings = bookingRepository.findByBookingStatus(status);
        } else if (propertyId != null) {
            bookings = bookingRepository.findByProperty_PropertyId(propertyId);
        } else if (userId != null) {
            bookings = bookingRepository.findByUser_Id(userId);
        } else {
            bookings = bookingRepository.findAll();
        }

        List<BookingDto> bookingDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(200, "Bookings retrieved successfully", bookingDtos);
    }

    @Override
    @Transactional
    public ApiResponse<BookingDto> updateBooking(Long bookingId, BookingRequestDto bookingRequestDto) {
        if (bookingId == null) {
            return new ApiResponse<>(400, "Booking ID is required", null);
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        // Only allow updates for PENDING bookings
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            return new ApiResponse<>(400, "Only pending bookings can be updated", null);
        }

        // Update dates if provided
        if (bookingRequestDto.getCheckInDate() != null && bookingRequestDto.getCheckOutDate() != null) {
            if (bookingRequestDto.getCheckOutDate().isBefore(bookingRequestDto.getCheckInDate())) {
                return new ApiResponse<>(400, "Check-out date must be after check-in date", null);
            }

            // Check availability for new dates
            ApiResponse<Boolean> availabilityCheck = checkAvailability(
                    booking.getPropertyId(),
                    booking.getRoomId(),
                    bookingRequestDto.getCheckInDate(),
                    bookingRequestDto.getCheckOutDate());

            if (!availabilityCheck.getData()) {
                return new ApiResponse<>(409, "Property/Room not available for selected dates", null);
            }

            booking.setCheckInDate(bookingRequestDto.getCheckInDate());
            booking.setCheckOutDate(bookingRequestDto.getCheckOutDate());

            // Recalculate pricing
            PriceCalculationRequestDto priceRequest = PriceCalculationRequestDto.builder()
                    .propertyId(booking.getPropertyId())
                    .roomId(booking.getRoomId())
                    .checkInDate(bookingRequestDto.getCheckInDate())
                    .checkOutDate(bookingRequestDto.getCheckOutDate())
                    .numberOfGuests(bookingRequestDto.getNumberOfGuests() != null
                            ? bookingRequestDto.getNumberOfGuests()
                            : booking.getNumberOfGuests())
                    .couponCode(bookingRequestDto.getCouponCode())
                    .build();

            ApiResponse<PriceBreakdownDto> priceResponse = calculatePrice(priceRequest);
            PriceBreakdownDto priceBreakdown = priceResponse.getData();

            booking.setNumberOfNights(priceBreakdown.getNumberOfNights());
            booking.setBasePrice(priceBreakdown.getBasePrice());
            booking.setExtraGuestCharges(priceBreakdown.getExtraGuestCharges());
            booking.setCleaningFee(priceBreakdown.getCleaningFee());
            booking.setServiceFee(priceBreakdown.getServiceFee());
            booking.setDiscountAmount(priceBreakdown.getDiscountAmount());
            booking.setTaxAmount(priceBreakdown.getTaxAmount());
            booking.setTotalAmount(priceBreakdown.getTotalAmount());
        }

        // Update guest counts if provided
        if (bookingRequestDto.getNumberOfGuests() != null) {
            booking.setNumberOfGuests(bookingRequestDto.getNumberOfGuests());
        }
        if (bookingRequestDto.getNumberOfAdults() != null) {
            booking.setNumberOfAdults(bookingRequestDto.getNumberOfAdults());
        }
        if (bookingRequestDto.getNumberOfChildren() != null) {
            booking.setNumberOfChildren(bookingRequestDto.getNumberOfChildren());
        }
        if (bookingRequestDto.getNumberOfInfants() != null) {
            booking.setNumberOfInfants(bookingRequestDto.getNumberOfInfants());
        }

        // Update special requests
        if (bookingRequestDto.getSpecialRequests() != null) {
            booking.setSpecialRequests(bookingRequestDto.getSpecialRequests());
        }

        Booking savedBooking = bookingRepository.save(booking);
        return new ApiResponse<>(200, "Booking updated successfully", bookingMapper.toDto(savedBooking));
    }

    @Override
    @Transactional
    public ApiResponse<BookingDto> confirmBooking(Long bookingId, BookingConfirmDto confirmDto) {
        if (bookingId == null) {
            return new ApiResponse<>(400, "Booking ID is required", null);
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            return new ApiResponse<>(400, "Only pending bookings can be confirmed", null);
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking.setConfirmedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return new ApiResponse<>(200, "Booking confirmed successfully", bookingMapper.toDto(savedBooking));
    }

    @Override
    @Transactional
    public ApiResponse<BookingDto> cancelBooking(Long bookingId, BookingCancelDto cancelDto, Long cancelledBy) {
        if (bookingId == null) {
            return new ApiResponse<>(400, "Booking ID is required", null);
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED
                || booking.getBookingStatus() == BookingStatus.REFUNDED) {
            return new ApiResponse<>(400, "Booking is already cancelled", null);
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(cancelDto.getCancellationReason());
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancelledBy(cancelledBy);

        Booking savedBooking = bookingRepository.save(booking);
        
        emailService.sendCancellationEmail(savedBooking.getUser().getEmail(),
        		                           savedBooking.getUser().getFullName(),
        		                           savedBooking.getProperty().getPropertyName());
        return new ApiResponse<>(200, "Booking cancelled successfully", bookingMapper.toDto(savedBooking));
    }
    
      

    @Override
    public ApiResponse<PriceBreakdownDto> calculatePrice(PriceCalculationRequestDto priceRequest) {

        // Validate inputs
        if (priceRequest.getPropertyId() == null) {
            return new ApiResponse<>(400, "Property ID is required", null);
        }
        if (priceRequest.getCheckInDate() == null || priceRequest.getCheckOutDate() == null) {
            return new ApiResponse<>(400, "Check-in and check-out dates are required", null);
        }

        // Get property
        Property property = propertyRepository.findById(priceRequest.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Property not found with ID: " + priceRequest.getPropertyId()));

        // Get room if specified, otherwise use property pricing
        Room room = null;
        BigDecimal pricePerNight;
        BigDecimal cleaningFee;
        BigDecimal serviceFee;
        Integer baseGuests;
        BigDecimal extraGuestFee;

        if (priceRequest.getRoomId() != null) {
            room = roomRepository.findById(priceRequest.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Room not found with ID: " + priceRequest.getRoomId()));
            pricePerNight = room.getPricePerNight();
            cleaningFee = BigDecimal.ZERO; // Rooms typically don't have cleaning fee
            serviceFee = BigDecimal.ZERO;
            baseGuests = room.getBaseGuests();
            extraGuestFee = room.getExtraGuestFee();
        } else {
            pricePerNight = property.getPricePerNight();
            cleaningFee = property.getCleaningFee();
            serviceFee = property.getServiceFee();
            baseGuests = property.getBaseGuests();
            extraGuestFee = property.getExtraGuestFee();
        }

        // Calculate number of nights
        long nights = ChronoUnit.DAYS.between(priceRequest.getCheckInDate(), priceRequest.getCheckOutDate());
        if (nights < 1) {
            return new ApiResponse<>(400, "Booking must be for at least 1 night", null);
        }

        // Calculate base price
        BigDecimal basePrice = pricePerNight.multiply(BigDecimal.valueOf(nights));

        // Calculate extra guest charges
        BigDecimal extraGuestCharges = BigDecimal.ZERO;
        if (priceRequest.getNumberOfGuests() != null && priceRequest.getNumberOfGuests() > baseGuests) {
            int extraGuests = priceRequest.getNumberOfGuests() - baseGuests;
            extraGuestCharges = extraGuestFee.multiply(BigDecimal.valueOf(extraGuests))
                    .multiply(BigDecimal.valueOf(nights));
        }

        // Calculate discount (stubbed for now - would integrate with coupon service)
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (priceRequest.getCouponCode() != null && !priceRequest.getCouponCode().isEmpty()) {
            // TODO: Integrate with coupon service to calculate actual discount
            // For now, return 0 discount
        }

        // Calculate tax
        BigDecimal subtotal = basePrice.add(extraGuestCharges).add(cleaningFee).add(serviceFee)
                .subtract(discountAmount);
        BigDecimal taxAmount = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        // Calculate total
        BigDecimal totalAmount = subtotal.add(taxAmount);

        PriceBreakdownDto breakdown = PriceBreakdownDto.builder()
                .numberOfNights((int) nights)
                .basePrice(basePrice.setScale(2, RoundingMode.HALF_UP))
                .extraGuestCharges(extraGuestCharges.setScale(2, RoundingMode.HALF_UP))
                .cleaningFee(cleaningFee.setScale(2, RoundingMode.HALF_UP))
                .serviceFee(serviceFee.setScale(2, RoundingMode.HALF_UP))
                .discountAmount(discountAmount.setScale(2, RoundingMode.HALF_UP))
                .taxAmount(taxAmount)
                .totalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP))
                .build();

        return new ApiResponse<>(200, "Price calculated successfully", breakdown);
    }

    @Override
    public ApiResponse<Boolean> checkAvailability(Long propertyId, Long roomId, LocalDate checkIn, LocalDate checkOut) {

        List<Booking> overlappingBookings;

        if (roomId != null) {
            overlappingBookings = bookingRepository.findOverlappingBookingsForRoom(roomId, checkIn, checkOut);
        } else {
            overlappingBookings = bookingRepository.findOverlappingBookingsForProperty(propertyId, checkIn, checkOut);
        }

        boolean isAvailable = overlappingBookings.isEmpty();
        String message = isAvailable
                ? "Property/Room is available"
                : "Property/Room is not available for the selected dates";

        return new ApiResponse<>(200, message, isAvailable);
    }

    @Override
    public ApiResponse<List<BookingDto>> getUserBookings(Long userId) {
        if (userId == null) {
            return new ApiResponse<>(400, "User ID is required", null);
        }

        List<Booking> bookings = bookingRepository.findByUser_Id(userId);
        List<BookingDto> bookingDtos = bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(200, "User bookings retrieved successfully", bookingDtos);
    }

    @Override
    @Transactional
    public ApiResponse<BookingDto> processRefund(Long bookingId, RefundRequestDto refundRequest) {
        if (bookingId == null) {
            return new ApiResponse<>(400, "Booking ID is required", null);
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if (booking.getBookingStatus() != BookingStatus.CANCELLED) {
            return new ApiResponse<>(400, "Only cancelled bookings can be refunded", null);
        }

        if (booking.getPaymentStatus() == PaymentStatus.REFUNDED) {
            return new ApiResponse<>(400, "Booking has already been refunded", null);
        }

        booking.setRefundAmount(refundRequest.getRefundAmount());
        booking.setRefundProcessedAt(LocalDateTime.now());
        booking.setPaymentStatus(PaymentStatus.REFUNDED);
        booking.setBookingStatus(BookingStatus.REFUNDED);

        Booking savedBooking = bookingRepository.save(booking);
        return new ApiResponse<>(200, "Refund processed successfully", bookingMapper.toDto(savedBooking));
    }
}
