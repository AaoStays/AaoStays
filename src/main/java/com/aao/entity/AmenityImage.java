package com.aao.entity;

import com.aao.enums.RelatedType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="AMENITY_IMAGES")
@Data

public class AmenityImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "property_id")
	private Property property;

	@Column(name = "name", length=100)
	private String name;
	
	@Column(name = "description", length=255)
	private String description;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "related_type")
	private RelatedType relatedType;
}