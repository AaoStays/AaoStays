package com.aao.serviceInterface;

import java.util.List;

import com.aao.dto.RoomAmenityRequest;
import com.aao.dto.RoomAmenityResponse;
import com.aao.response.ApiResponse;

public interface RoomAmenityService {

/*{take API response for all methods}*/
ApiResponse<RoomAmenityResponse> addAmenity(RoomAmenityRequest request);
ApiResponse<List<RoomAmenityResponse>> getAmenitiesByRoom(Long roomId);
ApiResponse<RoomAmenityResponse> updateAmenity(Long Id, RoomAmenityRequest request);
ApiResponse<String> deleteAmenity(Long id);
}