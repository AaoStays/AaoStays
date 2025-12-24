package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aao.entity.PropertyAmenity;

import java.util.List;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, Long> {

    boolean existsByPropertyIdAndAmenityId(Long propertyId, Long amenityId);

    List<PropertyAmenity> findByPropertyId(Long propertyId);

    List<PropertyAmenity> findByAmenityId(Long amenityId);
}
