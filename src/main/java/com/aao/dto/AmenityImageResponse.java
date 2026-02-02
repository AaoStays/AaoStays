package com.aao.dto;

import com.aao.enums.RelatedType;

public class AmenityImageResponse {

private Long id;
private Long propertyId;
private String name;
private String description;
private String imageName;
private RelatedType relatedType;

// Getters & Setters
public Long getId() { 
	return id; 
}
public void setId(Long Id) {
	this.id = Id; 
}
public Long getpropertyId() { 
	return propertyId; 
}
public void setpropertyId(Long propertyId) { 
	this.propertyId = propertyId; 
}

public String getname() { 
	return name; 
}
public void setname(String name) { 
	this.name = name; 
}

public String getdescription() { 
	return description; 
}
public void setdescription(String description) { 
	this.description = description; 
}

public String getimageName() { 
	return imageName; 
}
public void setimageName(String imageName) { 
	this.imageName = imageName; 
}
public RelatedType getRelatedType() {
	return relatedType;
}
public void setRelatedType(RelatedType relatedType) {
	this.relatedType = relatedType;
}
}