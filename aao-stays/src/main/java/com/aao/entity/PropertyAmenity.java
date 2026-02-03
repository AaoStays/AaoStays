package com.aao.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="propertyAmenity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAmenity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long propertyAmenityId;
	
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="is_available", nullable = false)
	private boolean isAvailable;

	
	@Column(name="notes", nullable = false)
	private String notes;
		
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="property_id" , nullable = false)
	private Property property;
	

}
