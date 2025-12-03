package com.aao.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.aao.entity.Property;
import com.aao.entity.PropertyImage;
import com.aao.dto.PropertyDto;
import com.aao.dto.PropertyImageDto;

@Component
public class PropertyMapper {

    public PropertyDto toDto(Property property) {
        if (property == null) return null;

        PropertyDto dto = new PropertyDto();

        dto.setPropertyId(property.getPropertyId());

        /* 🔥 SAFE: uses FK columns, no lazy loading */
        dto.setHostId(property.getHostId());
        dto.setAdminId(property.getAdminId());

        dto.setEmployeeId(property.getEmployeeId());
        dto.setPropertyName(property.getPropertyName());
        dto.setPropertyType(property.getPropertyType());
        dto.setCategoryType(property.getCategoryType());
        dto.setDescription(property.getDescription());
        dto.setAddressLine1(property.getAddressLine1());
        dto.setAddressLine2(property.getAddressLine2());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setCountry(property.getCountry());
        dto.setPostalCode(property.getPostalCode());
        dto.setLatitude(property.getLatitude());
        dto.setLongitude(property.getLongitude());
        dto.setLocationUrl(property.getLocationUrl());
        dto.setMapEmbedUrl(property.getMapEmbedUrl());
        dto.setBaseGuests(property.getBaseGuests());
        dto.setMaxGuests(property.getMaxGuests());
        dto.setExtraGuestAllowed(property.getExtraGuestAllowed());
        dto.setExtraGuestFee(property.getExtraGuestFee());
        dto.setBedrooms(property.getBedrooms());
        dto.setBeds(property.getBeds());
        dto.setBathrooms(property.getBathrooms());
        dto.setRestrooms(property.getRestrooms());
        dto.setKitchenType(property.getKitchenType());
        dto.setPetsAllowed(property.getPetsAllowed());
        dto.setSmokingAllowed(property.getSmokingAllowed());
        dto.setEventsAllowed(property.getEventsAllowed());
        dto.setPricePerNight(property.getPricePerNight());
        dto.setCleaningFee(property.getCleaningFee());
        dto.setServiceFee(property.getServiceFee());
        dto.setWeekendPrice(property.getWeekendPrice());
        dto.setWeeklyDiscountPercent(property.getWeeklyDiscountPercent());
        dto.setMonthlyDiscountPercent(property.getMonthlyDiscountPercent());
        dto.setCurrency(property.getCurrency());
        dto.setMinimumStay(property.getMinimumStay());
        dto.setMaximumStay(property.getMaximumStay());
        dto.setCheckInTime(property.getCheckInTime());
        dto.setCheckOutTime(property.getCheckOutTime());
        dto.setCancellationPolicy(property.getCancellationPolicy());
        dto.setInstantBooking(property.getInstantBooking());
        dto.setPropertyStatus(property.getPropertyStatus());
        dto.setApprovalStatus(property.getApprovalStatus());
        dto.setIsActive(property.getIsActive());
        dto.setIsFeatured(property.getIsFeatured());
        dto.setRatingAverage(property.getRatingAverage());
        dto.setTotalReviews(property.getTotalReviews());
        dto.setTotalBookings(property.getTotalBookings());
        dto.setViewsCount(property.getViewsCount());
        dto.setWishlistCount(property.getWishlistCount());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());
        dto.setPublishedAt(property.getPublishedAt());

        // ⭐ Image Mapping
        if (property.getImages() != null) {
            dto.setImages(
                property.getImages().stream().map(img -> {
                    PropertyImageDto d = new PropertyImageDto();
                    d.setImageId(img.getImageId());
                    d.setImageUrl(img.getImageUrl());
                    d.setImageCaption(img.getImageCaption());
                    d.setDisplayOrder(img.getDisplayOrder());
                    d.setIsPrimary(img.getIsPrimary());
                    return d;
                }).collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Property toEntity(PropertyDto dto) {
        if (dto == null) return null;

        Property property = new Property();

        property.setPropertyName(dto.getPropertyName());
        property.setPropertyType(dto.getPropertyType());
        property.setCategoryType(dto.getCategoryType());
        property.setDescription(dto.getDescription());
        property.setAddressLine1(dto.getAddressLine1());
        property.setAddressLine2(dto.getAddressLine2());
        property.setCity(dto.getCity());
        property.setState(dto.getState());
        property.setCountry(dto.getCountry());
        property.setPostalCode(dto.getPostalCode());
        property.setLatitude(dto.getLatitude());
        property.setLongitude(dto.getLongitude());
        property.setLocationUrl(dto.getLocationUrl());
        property.setMapEmbedUrl(dto.getMapEmbedUrl());
        property.setBaseGuests(dto.getBaseGuests());
        property.setMaxGuests(dto.getMaxGuests());
        property.setExtraGuestAllowed(dto.getExtraGuestAllowed());
        property.setExtraGuestFee(dto.getExtraGuestFee());
        property.setBedrooms(dto.getBedrooms());
        property.setBeds(dto.getBeds());
        property.setBathrooms(dto.getBathrooms());
        property.setRestrooms(dto.getRestrooms());
        property.setKitchenType(dto.getKitchenType());
        property.setPetsAllowed(dto.getPetsAllowed());
        property.setSmokingAllowed(dto.getSmokingAllowed());
        property.setEventsAllowed(dto.getEventsAllowed());
        property.setPricePerNight(dto.getPricePerNight());
        property.setCleaningFee(dto.getCleaningFee());
        property.setServiceFee(dto.getServiceFee());
        property.setWeekendPrice(dto.getWeekendPrice());
        property.setWeeklyDiscountPercent(dto.getWeeklyDiscountPercent());
        property.setMonthlyDiscountPercent(dto.getMonthlyDiscountPercent());
        property.setCurrency(dto.getCurrency());
        property.setMinimumStay(dto.getMinimumStay());
        property.setMaximumStay(dto.getMaximumStay());
        property.setCheckInTime(dto.getCheckInTime());
        property.setCheckOutTime(dto.getCheckOutTime());
        property.setCancellationPolicy(dto.getCancellationPolicy());
        property.setInstantBooking(dto.getInstantBooking());

        // ⭐ Images mapping
        if (dto.getImages() != null) {
            List<PropertyImage> images = dto.getImages().stream().map(imgDto -> {
                PropertyImage img = new PropertyImage();
                img.setImageUrl(imgDto.getImageUrl());
                img.setImageCaption(imgDto.getImageCaption());
                img.setDisplayOrder(imgDto.getDisplayOrder());
                img.setIsPrimary(imgDto.getIsPrimary());
                img.setProperty(property);
                return img;
            }).toList();

            property.setImages(images);
        }

        return property;
    }
}
