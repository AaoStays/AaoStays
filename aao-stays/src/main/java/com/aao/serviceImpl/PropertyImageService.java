package com.aao.serviceImpl;

import com.aao.entity.Property;
import com.aao.entity.PropertyImage;
import com.aao.repo.PropertyImageRepo;
import com.aao.repo.PropertyRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final Cloudinary cloudinary;
    private final PropertyImageRepo propertyImageRepo;
    private final PropertyRepository propertyRepository;

   
    @Transactional
    public List<PropertyImage> uploadImages(Long propertyId, MultipartFile[] files) {

        if (propertyId == null || propertyId <= 0) {
            throw new IllegalArgumentException("Property ID cannot be null or invalid");
        }

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No images received to upload");
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));

        // Get the next display order
        int nextOrder = Optional
                .ofNullable(propertyImageRepo.findTopByPropertyPropertyIdOrderByDisplayOrderDesc(propertyId))
                .map(PropertyImage::getDisplayOrder)
                .orElse(0);

        List<PropertyImage> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("folder", "aao/properties/" + propertyId)
                );

                String imageUrl = (String) uploadResult.get("secure_url");

                if (imageUrl == null) {
                    throw new IllegalStateException("Cloudinary did not return a valid URL");
                }

                PropertyImage image = new PropertyImage();
                image.setProperty(property);
                image.setImageUrl(imageUrl);
                image.setDisplayOrder(++nextOrder);
                image.setIsPrimary(false);

                savedImages.add(propertyImageRepo.save(image));

            } catch (Exception e) {
                // This will be caught by your @ExceptionHandler(Exception.class)
                throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename());
            }
        }

        return savedImages;
    }

    // 🚀 Get All Images for a Property
    public List<PropertyImage> getImagesByProperty(Long propertyId) {

        if (propertyId == null || propertyId <= 0) {
            throw new IllegalArgumentException("Property ID cannot be null or invalid");
        }

        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));

        return propertyImageRepo.findByProperty_PropertyIdOrderByDisplayOrderAsc(propertyId);
    }

    // 🚀 Set Primary Image
    @Transactional
    public PropertyImage setPrimaryImage(Long imageId) {

        if (imageId == null) {
            throw new IllegalArgumentException("Image ID cannot be null");
        }

        PropertyImage img = propertyImageRepo.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID: " + imageId));

        Long propertyId = img.getProperty().getPropertyId();

        List<PropertyImage> allImages = propertyImageRepo.findByPropertyPropertyId(propertyId);
        allImages.forEach(i -> i.setIsPrimary(false));
        propertyImageRepo.saveAll(allImages);

        img.setIsPrimary(true);
        return propertyImageRepo.save(img);
    }

    // 🚀 Delete Image
    @Transactional
    public void deleteImage(Long imageId) {

        if (imageId == null) {
            throw new IllegalArgumentException("Image ID cannot be null");
        }

        PropertyImage img = propertyImageRepo.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with ID: " + imageId));

        propertyImageRepo.delete(img);
    }
    
    
}
