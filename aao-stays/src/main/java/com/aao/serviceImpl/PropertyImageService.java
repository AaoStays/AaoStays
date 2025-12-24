package com.aao.serviceImpl;

import com.aao.entity.Host;
import com.aao.entity.Property;
import com.aao.entity.PropertyImage;
import com.aao.entity.User;
import com.aao.repo.HostRepo;
import com.aao.repo.PropertyImageRepo;
import com.aao.repo.PropertyRepository;
import com.aao.security.CustomUserDetails;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sun.security.auth.UserPrincipal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final Cloudinary cloudinary;
    private final PropertyImageRepo propertyImageRepo;
    private final PropertyRepository propertyRepository;
    private final HostRepo hostRepo;

   
    @Transactional
    public List<PropertyImage> uploadImages(Long propertyId, MultipartFile[] files) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User loggedInUser = userDetails.getUser(); 

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (propertyId == null || propertyId <= 0) {
            throw new IllegalArgumentException("Property ID cannot be null or invalid");
        }

        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No images received to upload");
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Property not found with ID: " + propertyId));

       
        if (!isAdmin) {

            Host host = hostRepo.findByUser_Id(loggedInUser.getId())
                    .orElseThrow(() ->
                            new AccessDeniedException("Host profile not found"));

            if (!property.getHostId().equals(host.getHostId())) {
                throw new AccessDeniedException(
                        "You are not allowed to upload images for this property"
                );
            }
        }

        int nextOrder = Optional
                .ofNullable(
                        propertyImageRepo
                                .findTopByPropertyPropertyIdOrderByDisplayOrderDesc(propertyId)
                )
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
                throw new RuntimeException(
                        "Failed to upload image: " + file.getOriginalFilename(), e);
            }
        }

        return savedImages;
    }

    public List<PropertyImage> getImagesByProperty(Long propertyId) {
    	

    

        if (propertyId == null || propertyId <= 0) {
            throw new IllegalArgumentException("Property ID cannot be null or invalid");
        }

        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));

        return propertyImageRepo.findByProperty_PropertyIdOrderByDisplayOrderAsc(propertyId);
    }

  
    @Transactional
    public PropertyImage setPrimaryImage(Long imageId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Unauthenticated");
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) auth.getPrincipal();

        User loggedInUser = userDetails.getUser();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isHost = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_HOST"));

        if (!isAdmin && !isHost) {
            throw new AccessDeniedException("Only admin or host can perform this action");
        }

        PropertyImage img = propertyImageRepo.findById(imageId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Image not found with ID: " + imageId));

        Property property = img.getProperty();

     
        if (isHost && !isAdmin) {
            Host host = hostRepo.findByUser_Id(loggedInUser.getId())
                    .orElseThrow(() ->
                            new AccessDeniedException("Host profile not found"));

            if (!property.getHostId().equals(host.getHostId())) {
                throw new AccessDeniedException(
                        "You cannot modify images of another host’s property"
                );
            }
        }

     
        List<PropertyImage> allImages =
                propertyImageRepo.findByPropertyPropertyId(property.getPropertyId());

        allImages.forEach(i -> i.setIsPrimary(false));
        propertyImageRepo.saveAll(allImages);

        img.setIsPrimary(true);
        return propertyImageRepo.save(img);
    }

    
    @Transactional
    public void deleteImage(Long imageId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthenticated");
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User loggedInUser = userDetails.getUser();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isHost = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_HOST"));

        if (!isAdmin && !isHost) {
            throw new AccessDeniedException("Access denied");
        }

        if (imageId == null) {
            throw new IllegalArgumentException("Image ID cannot be null");
        }

        PropertyImage img = propertyImageRepo.findById(imageId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Image not found with ID: " + imageId));

        if (isHost && !isAdmin) {

            Host host = hostRepo.findByUser_Id(loggedInUser.getId())
                    .orElseThrow(() ->
                            new AccessDeniedException("Host profile not found"));

            Long propertyHostId = img.getProperty().getHostId();

            if (!propertyHostId.equals(host.getHostId())) {
                throw new AccessDeniedException(
                        "You are not allowed to delete images of another host’s property"
                );
            }
        }

        propertyImageRepo.delete(img);
    }

    
}
