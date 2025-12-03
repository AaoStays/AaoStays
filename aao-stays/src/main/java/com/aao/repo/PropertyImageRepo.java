package com.aao.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aao.entity.PropertyImage;

public interface PropertyImageRepo extends JpaRepository<PropertyImage, Long> {
	 List<PropertyImage> findByProperty_PropertyIdOrderByDisplayOrderAsc(Long propertyId);

	    PropertyImage findTopByPropertyPropertyIdOrderByDisplayOrderDesc(Long propertyId);

	    List<PropertyImage> findByPropertyPropertyId(Long propertyId);
}
