package com.aao.serviceInterface;

import com.aao.dto.PriceRangeDto;
import com.aao.dto.RoomRequestDto;
import com.aao.dto.RoomResponseDto;
import com.aao.dto.RoomStatusUpdateDto;
import com.aao.entity.RoomStatus;
import com.aao.response.ApiResponse;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {

    ApiResponse<RoomResponseDto> addRoom(Long propertyId, RoomRequestDto roomRequestDto);

    ApiResponse<List<RoomResponseDto>> getAllRoomsByPropertyId(Long propertyId);

    ApiResponse<List<RoomResponseDto>> getAvailableRoomsByPropertyId(Long propertyId);

    ApiResponse<List<RoomResponseDto>> searchRooms(
            String roomType,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            RoomStatus roomStatus);

    ApiResponse<RoomResponseDto> getRoomById(Long roomId);

    ApiResponse<RoomResponseDto> updateRoom(Long roomId, RoomRequestDto roomRequestDto);

    ApiResponse<RoomResponseDto> updateRoomStatus(Long roomId, RoomStatusUpdateDto statusUpdateDto);

    ApiResponse<Void> deleteRoom(Long roomId);

    ApiResponse<PriceRangeDto> getPropertyRoomPriceRange(Long propertyId);
}
