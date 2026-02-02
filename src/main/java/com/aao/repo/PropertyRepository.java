package com.aao.repo;

import com.aao.entity.ApprovalStatus;
import com.aao.entity.Property;
import com.aao.entity.PropertyImage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
	
	   List<Property> findByHost_HostId(Long hostId);

	    List<Property> findByCityIgnoreCase(String city);

	    List<Property> findByApprovalStatus(ApprovalStatus status);
	    
	    @Query("""
	    	       SELECT p FROM Property p
	    	       WHERE (:city IS NULL OR LOWER(COALESCE(p.city, '')) = LOWER(:city))
	    	       AND (:state IS NULL OR LOWER(COALESCE(p.state, '')) = LOWER(:state))
	    	       AND (:categoryType IS NULL OR LOWER(COALESCE(p.categoryType, '')) = LOWER(:categoryType))
	    	       AND (:propertyType IS NULL OR LOWER(COALESCE(p.propertyType, '')) = LOWER(:propertyType))
	    	       AND (:minPrice IS NULL OR p.pricePerNight >= :minPrice)
	    	       AND (:maxPrice IS NULL OR p.pricePerNight <= :maxPrice)
	    	       """)
	    	List<Property> searchProperties(
	    	        String city,
	    	        String state,
	    	        String categoryType,
	    	        String propertyType,
	    	        BigDecimal minPrice,
	    	        BigDecimal maxPrice
	    	);


	    
	    
}
