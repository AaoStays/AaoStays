package com.aao.serviceImpl;

import com.aao.dto.PriceRangeDto;
import com.aao.dto.RoomRequestDto;
import com.aao.dto.RoomResponseDto;
import com.aao.dto.RoomStatusUpdateDto;
import com.aao.entity.Property;
import com.aao.entity.Room;
import com.aao.entity.RoomStatus;
import com.aao.repo.PropertyRepository;
import com.aao.repo.RoomRepository;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.RoomService;
import com.aao.utils.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional
    public ApiResponse<RoomResponseDto> addRoom(Long propertyId, RoomRequestDto roomRequestDto) {
        // Validate property exists
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));

        // Validate required fields
        if (roomRequestDto.getPricePerNight() == null) {
            throw new IllegalArgumentException("Price per night is required");
        }

        // Map DTO to entity
        Room room = roomMapper.toEntity(roomRequestDto);
        room.setProperty(property);

        // Set defaults if not provided
        if (room.getRoomStatus() == null) {
            room.setRoomStatus(RoomStatus.AVAILABLE);
        }

        Room savedRoom = roomRepository.save(room);
        return new ApiResponse<>(201, "Room added successfully", roomMapper.toDto(savedRoom));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<RoomResponseDto>> getAllRoomsByPropertyId(Long propertyId) {
        // Validate property exists
        if (!propertyRepository.existsById(propertyId)) {
            throw new IllegalArgumentException("Property not found with ID: " + propertyId);
        } 
        

        List<Room> rooms = roomRepository.findByProperty_PropertyId(propertyId);
        List<RoomResponseDto> roomDtos = rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());

        if (roomDtos.isEmpty()) {
            return new ApiResponse<>(404, "No rooms found for this property", null);
        }

        return new ApiResponse<>(200, "Rooms retrieved successfully", roomDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<RoomResponseDto>> getAvailableRoomsByPropertyId(Long propertyId) {
        // Validate property exists
        if (!propertyRepository.existsById(propertyId)) {
            throw new IllegalArgumentException("Property not found with ID: " + propertyId);
        }

        List<Room> rooms = roomRepository.findByProperty_PropertyIdAndRoomStatus(propertyId, RoomStatus.AVAILABLE);
        List<RoomResponseDto> roomDtos = rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());

        if (roomDtos.isEmpty()) {
            return new ApiResponse<>(404, "No available rooms found for this property", null);
        }

        return new ApiResponse<>(200, "Available rooms retrieved successfully", roomDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<RoomResponseDto>> searchRooms(
            String roomType,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            RoomStatus roomStatus) {

        
        if (roomType != null && !roomType.isBlank()) {
            roomType = roomType.trim().toLowerCase();
        } else {
            roomType = null;
        }

        List<Room> rooms = roomRepository.searchRooms(roomType, minPrice, maxPrice, roomStatus);

      
        if (rooms.isEmpty()) {
            return new ApiResponse<>(200, "No rooms matched filter criteria", List.of());
        }

        List<RoomResponseDto> roomDtos = rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(200, "Rooms retrieved successfully", roomDtos);
    }


    @Override
    @Transactional(readOnly = true)
    public ApiResponse<RoomResponseDto> getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        return new ApiResponse<>(200, "Room details retrieved successfully", roomMapper.toDto(room));
    }

    @Override
    @Transactional
    public ApiResponse<RoomResponseDto> updateRoom(Long roomId, RoomRequestDto roomRequestDto) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        // Update fields if provided
        if (roomRequestDto.getRoomNumber() != null)
            existingRoom.setRoomNumber(roomRequestDto.getRoomNumber());
        if (roomRequestDto.getRoomType() != null)
            existingRoom.setRoomType(roomRequestDto.getRoomType());
        if (roomRequestDto.getRoomName() != null)
            existingRoom.setRoomName(roomRequestDto.getRoomName());
        if (roomRequestDto.getRoomDescription() != null)
            existingRoom.setRoomDescription(roomRequestDto.getRoomDescription());
        if (roomRequestDto.getBaseGuests() != null)
            existingRoom.setBaseGuests(roomRequestDto.getBaseGuests());
        if (roomRequestDto.getMaxGuests() != null)
            existingRoom.setMaxGuests(roomRequestDto.getMaxGuests());
        if (roomRequestDto.getExtraGuestAllowed() != null)
            existingRoom.setExtraGuestAllowed(roomRequestDto.getExtraGuestAllowed());
        if (roomRequestDto.getExtraGuestFee() != null)
            existingRoom.setExtraGuestFee(roomRequestDto.getExtraGuestFee());
        if (roomRequestDto.getPricePerNight() != null)
            existingRoom.setPricePerNight(roomRequestDto.getPricePerNight());
        if (roomRequestDto.getBedType() != null)
            existingRoom.setBedType(roomRequestDto.getBedType());
        if (roomRequestDto.getBedCount() != null)
            existingRoom.setBedCount(roomRequestDto.getBedCount());
        if (roomRequestDto.getRoomSizeSqft() != null)
            existingRoom.setRoomSizeSqft(roomRequestDto.getRoomSizeSqft());
        if (roomRequestDto.getHasBalcony() != null)
            existingRoom.setHasBalcony(roomRequestDto.getHasBalcony());
        if (roomRequestDto.getHasWindow() != null)
            existingRoom.setHasWindow(roomRequestDto.getHasWindow());
        if (roomRequestDto.getFloorNumber() != null)
            existingRoom.setFloorNumber(roomRequestDto.getFloorNumber());

        Room updatedRoom = roomRepository.save(existingRoom);
        return new ApiResponse<>(200, "Room updated successfully", roomMapper.toDto(updatedRoom));
    }

    @Override
    @Transactional
    public ApiResponse<RoomResponseDto> updateRoomStatus(Long roomId, RoomStatusUpdateDto statusUpdateDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        if (statusUpdateDto.getRoomStatus() == null) {
            throw new IllegalArgumentException("Room status is required");
        }

        room.setRoomStatus(statusUpdateDto.getRoomStatus());
        Room updatedRoom = roomRepository.save(room);

        return new ApiResponse<>(200, "Room status updated successfully", roomMapper.toDto(updatedRoom));
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }

        roomRepository.deleteById(roomId);
        return new ApiResponse<>(200, "Room deleted successfully", null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<PriceRangeDto> getPropertyRoomPriceRange(Long propertyId) {
        // Validate property exists
        if (!propertyRepository.existsById(propertyId)) {
            throw new IllegalArgumentException("Property not found with ID: " + propertyId);
        }

        Object[] priceRange = roomRepository.findPriceRangeByPropertyId(propertyId);

        if (priceRange == null || priceRange[0] == null || priceRange[1] == null) {
            return new ApiResponse<>(404, "No rooms found for this property", null);
        }

        BigDecimal minPrice = (BigDecimal) priceRange[0];
        BigDecimal maxPrice = (BigDecimal) priceRange[1];

        PriceRangeDto priceRangeDto = new PriceRangeDto(minPrice, maxPrice);
        return new ApiResponse<>(200, "Price range retrieved successfully", priceRangeDto);
    }
}
