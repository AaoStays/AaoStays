package com.aao.controller;

import com.aao.dto.PriceRangeDto;
import com.aao.dto.RoomRequestDto;
import com.aao.dto.RoomResponseDto;
import com.aao.dto.RoomStatusUpdateDto;
import com.aao.entity.RoomStatus;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

 
    @PostMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
    public ResponseEntity<ApiResponse<RoomResponseDto>> addRoom(
            @PathVariable Long propertyId,
            @RequestBody RoomRequestDto roomRequestDto) {
        ApiResponse<RoomResponseDto> response = roomService.addRoom(propertyId, roomRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
  
     
   
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<RoomResponseDto>>> getAllRoomsByPropertyId(
            @PathVariable Long propertyId) {
        ApiResponse<List<RoomResponseDto>> response = roomService.getAllRoomsByPropertyId(propertyId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/property/{propertyId}/available")
    public ResponseEntity<ApiResponse<List<RoomResponseDto>>> getAvailableRoomsByPropertyId(
            @PathVariable Long propertyId) {
        ApiResponse<List<RoomResponseDto>> response = roomService.getAvailableRoomsByPropertyId(propertyId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RoomResponseDto>>> searchRooms(
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) RoomStatus roomStatus) {
        ApiResponse<List<RoomResponseDto>> response = roomService.searchRooms(roomType, minPrice, maxPrice, roomStatus);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

   
    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> getRoomById(@PathVariable Long roomId) {
        ApiResponse<RoomResponseDto> response = roomService.getRoomById(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
    public ResponseEntity<ApiResponse<RoomResponseDto>> updateRoom(
            @PathVariable Long roomId,
            @RequestBody RoomRequestDto roomRequestDto) {
        ApiResponse<RoomResponseDto> response = roomService.updateRoom(roomId, roomRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   
    @PatchMapping("/{roomId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
    public ResponseEntity<ApiResponse<RoomResponseDto>> updateRoomStatus(
            @PathVariable Long roomId,
            @RequestBody RoomStatusUpdateDto statusUpdateDto) {
        ApiResponse<RoomResponseDto> response = roomService.updateRoomStatus(roomId, statusUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOST')")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long roomId) {
        ApiResponse<Void> response = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/property/{propertyId}/price-range")
    public ResponseEntity<ApiResponse<PriceRangeDto>> getPropertyRoomPriceRange(
            @PathVariable Long propertyId) {
        ApiResponse<PriceRangeDto> response = roomService.getPropertyRoomPriceRange(propertyId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
