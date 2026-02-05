package com.aao.utils;

import com.aao.dto.RoomAmenityRequest;
import com.aao.dto.RoomAmenityResponse;
import com.aao.entity.RoomAmenity;

public class RoomAmenityMapper {

    public static RoomAmenity toEntity(RoomAmenityRequest req) {
        RoomAmenity amenity = new RoomAmenity();
        amenity.setAmenityName(req.getAmenityName());
        amenity.setAmenityIcon(req.getAmenityIcon());
        amenity.setIsAvailable(req.getIsAvailable());
        amenity.setNotes(req.getNotes());
        return amenity;
    }

public static RoomAmenityResponse toResponse(RoomAmenity a) 
{
RoomAmenityResponse res = new RoomAmenityResponse();
    	    res.setRoomAmenityId(a.getRoomAmenityId());
    	    res.setRoomId(a.getRoom().getRoomId());
    	    res.setAmenityName(a.getAmenityName());
    	    res.setAmenityIcon(a.getAmenityIcon());
              res.setIsAvailable(a.getIsAvailable());
              res.setNotes(a.getNotes());
              return res;
}
}