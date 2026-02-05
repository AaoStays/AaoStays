package com.aao.dto;
import com.aao.enums.RelatedType;

import lombok.Data;

@Data

public class AmenityImageRequest {

		private Long PropertyId;
		private String Name;
		private String Description;
		private String ImageName;
		private RelatedType RelatedType;
}