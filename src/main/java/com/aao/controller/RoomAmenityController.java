package com.aao.controller;

import com.aao.dto.RoomAmenityRequest;
import com.aao.dto.RoomAmenityResponse;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.RoomAmenityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-amenities")
public class RoomAmenityController {

private final RoomAmenityService service;

public RoomAmenityController(RoomAmenityService service) {
this.service = service;
}

@PostMapping
public ApiResponse<RoomAmenityRequest> add(@RequestBody RoomAmenityRequest request) {
return service.addAmenity(request);
}

@GetMapping("/room/{roomId}")
public ApiResponse<List<RoomAmenityResponse>> getByRoom(@PathVariable Long roomId) {
return service.getAmenitiesByRoom(roomId);
}

@PutMapping("/{id}")
public ApiResponse<RoomAmenityResponse> update(
@PathVariable Long id,
@RequestBody RoomAmenityRequest request) {
return service.updateAmenity(id, request);
}

@DeleteMapping("/{id}")
public ApiResponse<String> delete(@PathVariable Long id) {
return service.deleteAmenity(id);
}
}