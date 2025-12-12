package com.aao.repo;

import com.aao.entity.Room;
import com.aao.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByProperty_PropertyId(Long propertyId);

    List<Room> findByProperty_PropertyIdAndRoomStatus(Long propertyId, RoomStatus status);

    @Query("""
            SELECT r FROM Room r
            WHERE (:roomType IS NULL OR LOWER(COALESCE(r.roomType, '')) = :roomType)
            AND (:minPrice IS NULL OR r.pricePerNight >= :minPrice)
            AND (:maxPrice IS NULL OR r.pricePerNight <= :maxPrice)
            AND (:roomStatus IS NULL OR r.roomStatus = :roomStatus)
            """)
    List<Room> searchRooms(
            @Param("roomType") String roomType,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("roomStatus") RoomStatus roomStatus);


    @Query("""
            SELECT MIN(r.pricePerNight), MAX(r.pricePerNight)
            FROM Room r
            WHERE r.property.propertyId = :propertyId
            """)
    Object[] findPriceRangeByPropertyId(@Param("propertyId") Long propertyId);
}
