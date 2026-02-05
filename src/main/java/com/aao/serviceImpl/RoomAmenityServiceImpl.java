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

    @Override
    public ApiResponse<RoomAmenityResponse> addAmenity(RoomAmenityRequest request) {

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomAmenity amenity = RoomAmenityMapper.toEntity(request);

        
        amenity.setRoom(room);

        RoomAmenity saved = repository.save(amenity);

        return new ApiResponse<RoomAmenityResponse>(201,"Amenity added successfully",RoomAmenityMapper.toResponse(saved));
    }

    @Override
    public ApiResponse<List<RoomAmenityResponse>> getAmenitiesByRoom(Long roomId) {

        List<RoomAmenityResponse> list =
                repository.findByRoom_RoomId(roomId)
                        .stream()
                        .map(RoomAmenityMapper::toResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>(200,"Amenities fetched successfully",list);
    }

    @Override
    public ApiResponse<RoomAmenityResponse> updateAmenity(Long id, RoomAmenityRequest request) {

        RoomAmenity amenity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));

        amenity.setAmenityName(request.getAmenityName());
        amenity.setAmenityIcon(request.getAmenityIcon());
        amenity.setIsAvailable(request.getIsAvailable());
        amenity.setNotes(request.getNotes());

        RoomAmenity updated = repository.save(amenity);

        return new ApiResponse<>(200,"Amenity updated successfully",RoomAmenityMapper.toResponse(updated));
    }

    @Override
    public ApiResponse<String> deleteAmenity(Long id) {

        repository.deleteById(id);

        return new ApiResponse<>(200,"Amenity deleted successfully","Deleted");
    }
}