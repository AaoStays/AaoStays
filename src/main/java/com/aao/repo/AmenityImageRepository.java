package com.aao.repo;

import com.aao.entity.AmenityImage;
import com.aao.enums.RelatedType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenityImageRepository extends JpaRepository<AmenityImage, Long> {

    List<AmenityImage> findByPropertyId(Long propertyId);

    List<AmenityImage> findByPropertyIdAndRelatedType(Long propertyId, RelatedType relatedType);
}