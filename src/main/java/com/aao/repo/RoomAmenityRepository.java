package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aao.entity.RoomAmenity;
import java.util.List;

@Repository
public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, Long> 
{
	List<RoomAmenity> findByRoomId(Long roomId);

}