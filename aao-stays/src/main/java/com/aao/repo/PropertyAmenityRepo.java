
package com.aao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aao.entity.PropertyAmenity;

public interface PropertyAmenityRepo extends JpaRepository<PropertyAmenity, Long> {

    List<PropertyAmenity> findByProperty_PropertyId(Long propertyId);

    boolean existsByProperty_PropertyIdAndNameIgnoreCase(Long propertyId, String name);
}

