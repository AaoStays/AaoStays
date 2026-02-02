package com.aao.serviceImpl;

import com.aao.dto.RoomAmenityRequest;
import com.aao.dto.RoomAmenityResponse;
import com.aao.entity.Room;
import com.aao.entity.RoomAmenity;
import com.aao.repo.RoomAmenityRepository;
import com.aao.repo.RoomRepository;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.RoomAmenityService;
import com.aao.utils.RoomAmenityMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomAmenityServiceImpl implements RoomAmenityService {

private final RoomAmenityRepository repository;
private final RoomRepository roomRepository;
private final RoomAmenityMapper amenityMapper;
@Override
public ApiResponse <RoomAmenityRequest>addAmenity(RoomAmenityRequest request) {

Room room = roomRepository.findById(request.getRoomId())
.orElseThrow(() -> new RuntimeException("Room not found"));

RoomAmenity amenity = RoomAmenityMapper.toEntity(request);

RoomAmenity saved = repository.save(amenity);

return new ApiResponse<RoomAmenityRequest>(201, "amenity added successfully", amenityMapper.toResponse(amenity));
}

@Override
public ApiResponse<List<RoomAmenityResponse>> getAmenitiesByRoom(Long roomId) {

List<RoomAmenityResponse> list = repository.findByRoomId(roomId)
.stream()
.map(RoomAmenityMapper::toResponse)
.collect(Collectors.toList());

return new ApiResponse<>(true, "Amenities fetched successfully", list);
}

@Override
public ApiResponse<RoomAmenityResponse> updateAmenity(Long id, RoomAmenityRequest request) {

RoomAmenity amenity = repository.findById(id)
.orElseThrow(() -> new RuntimeException("Amenity not found"));

amenity.setAmenityName(request.getAmenityName());
amenity.setAmenityIcon(request.getAmenityIcon());
amenity.setIsAvailable(request.getIsAvailable());
amenity.setNotes(request.getNotes());

return new ApiResponse<>(true, "Amenity updated successfully",RoomAmenityMapper.toResponse(repository.save(amenity)));
}

@Override
public ApiResponse<String> deleteAmenity(Long id) {
repository.deleteById(id);
return new ApiResponse<>(true, "Amenity deleted successfully", "Deleted");
}
}