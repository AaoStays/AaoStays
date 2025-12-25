package com.aao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Collection;

import com.aao.dto.PropertyAmenityDTO;

@Entity
@Table(
        name = "property_amenity",
        uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "amenity_id"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyAmenityId;

    @Column(nullable = false)
    private Long propertyId;

    @Column(nullable = false)
    private Long amenityId;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    private String notes;

    @Column(updatable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

	public Collection<PropertyAmenityDTO> findByPropertyId1(Long propertyId2) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean existsByPropertyIdAndAmenityId(Long propertyId2, Long amenityId2) {
		// TODO Auto-generated method stub
		return false;
	}

	public PropertyAmenity save(PropertyAmenity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Collection<PropertyAmenityDTO> findByPropertyId(Long propertyId2) {
		// TODO Auto-generated method stub
		return null;
	}
}
