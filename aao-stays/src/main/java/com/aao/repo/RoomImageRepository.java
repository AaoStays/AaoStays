package com.aao.repo;

import com.aao.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    List<RoomImage> findByRoom_RoomIdOrderByDisplayOrderAsc(Long roomId);
}
