package com.aao.dto;

import lombok.Data;

@Data
public class PropertyImageDto {
	  private Long imageId;
	    private String imageUrl;
	    private String imageCaption;
	    private Integer displayOrder;
	    private Boolean isPrimary;
}
