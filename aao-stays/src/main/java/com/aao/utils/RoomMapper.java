package com.aao.utils;

import com.aao.dto.RoomImageDto;
import com.aao.dto.RoomRequestDto;
import com.aao.dto.RoomResponseDto;
import com.aao.entity.Room;
import com.aao.entity.RoomImage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequestDto dto) {
        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setRoomName(dto.getRoomName());
        room.setRoomDescription(dto.getRoomDescription());
        room.setBaseGuests(dto.getBaseGuests());
        room.setMaxGuests(dto.getMaxGuests());
        room.setExtraGuestAllowed(dto.getExtraGuestAllowed());
        room.setExtraGuestFee(dto.getExtraGuestFee());
        room.setPricePerNight(dto.getPricePerNight());
        room.setBedType(dto.getBedType());
        room.setBedCount(dto.getBedCount());
        room.setRoomSizeSqft(dto.getRoomSizeSqft());
        room.setHasBalcony(dto.getHasBalcony());
        room.setHasWindow(dto.getHasWindow());
        room.setFloorNumber(dto.getFloorNumber());
        return room;
    }

    public RoomResponseDto toDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setRoomId(room.getRoomId());
       
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setRoomName(room.getRoomName());
        dto.setRoomDescription(room.getRoomDescription());
        dto.setBaseGuests(room.getBaseGuests());
        dto.setMaxGuests(room.getMaxGuests());
        dto.setExtraGuestAllowed(room.getExtraGuestAllowed());
        dto.setExtraGuestFee(room.getExtraGuestFee());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setBedType(room.getBedType());
        dto.setBedCount(room.getBedCount());
        dto.setRoomSizeSqft(room.getRoomSizeSqft());
        dto.setHasBalcony(room.getHasBalcony());
        dto.setHasWindow(room.getHasWindow());
        dto.setFloorNumber(room.getFloorNumber());
        dto.setRoomStatus(room.getRoomStatus());
        dto.setIsActive(room.getIsActive());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setUpdatedAt(room.getUpdatedAt());

        // Map images if available
        if (room.getImages() != null) {
            List<RoomImageDto> imageDtos = room.getImages().stream()
                    .map(this::toImageDto)
                    .collect(Collectors.toList());
            dto.setImages(imageDtos);
        } else {
            dto.setImages(Collections.emptyList());
        }

        return dto;
    }

    public RoomImageDto toImageDto(RoomImage image) {
        RoomImageDto dto = new RoomImageDto();
        dto.setImageId(image.getImageId());
        dto.setImageUrl(image.getImageUrl());
        dto.setImageCaption(image.getImageCaption());
        dto.setDisplayOrder(image.getDisplayOrder());
        dto.setIsPrimary(image.getIsPrimary());
        dto.setCreatedAt(image.getCreatedAt());
        return dto;
    }
}
