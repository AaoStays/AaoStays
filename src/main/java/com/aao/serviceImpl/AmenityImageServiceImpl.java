package com.aao.serviceImpl;


import com.aao.dto.AmenityImageRequest;
import com.aao.dto.AmenityImageResponse;
import com.aao.entity.AmenityImage;
import com.aao.enums.RelatedType;
import com.aao.utils.AmenityImageMapper;
import com.aao.repo.AmenityImageRepository;
import com.aao.serviceInterface.AmenityImageService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmenityImageServiceImpl implements AmenityImageService {

    private final AmenityImageRepository repository;

    public AmenityImageServiceImpl(AmenityImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public AmenityImageResponse save(AmenityImageRequest dto) {
        AmenityImage image = AmenityImageMapper.toEntity(dto);
        return AmenityImageMapper.toDTO(repository.save(image));
    }

    @Override
    public List<AmenityImageResponse> getByPropertyId(Long propertyId) {
        return repository.findByPropertyId(propertyId)
                .stream()
                .map(AmenityImageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityImageResponse> getByPropertyAndType(Long propertyId, RelatedType relatedType) {
        return repository.findByPropertyIdAndRelatedType(propertyId, relatedType)
                .stream()
                .map(AmenityImageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}