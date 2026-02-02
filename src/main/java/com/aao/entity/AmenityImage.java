package com.aao.entity;

import com.aao.enums.RelatedType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="AMENITY_IMAGES")
@Data

public class AmenityImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@Column(name = "property_id")
	private Long propertyId;

	@Column(name = "name", length=100)
	private String name;
	
	@Column(name = "description", length=255)
	private String description;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "related_type")
	private RelatedType relatedType;
}