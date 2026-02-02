package com.aao.serviceInterface;

import com.aao.dto.AmenityImageRequest;
import com.aao.dto.AmenityImageResponse;
import com.aao.enums.RelatedType;

import java.util.List;

public interface AmenityImageService {

    AmenityImageResponse save(AmenityImageRequest dto);

    List<AmenityImageResponse> getByPropertyId(Long propertyId);

    List<AmenityImageResponse> getByPropertyAndType(Long propertyId, RelatedType relatedType);

    void delete(Long id);
}