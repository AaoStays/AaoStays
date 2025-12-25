package com.aao.serviceImpl;

import com.aao.dto.WishlistResponseDto;

import java.util.List;

public interface WishlistService {

    WishlistResponseDto addToWishlist(Long userId, Long propertyId);

    void removeFromWishlist(Long userId, Long propertyId);

    List<WishlistResponseDto> getUserWishlist(Long userId);

    boolean isPropertyWishlisted(Long userId, Long propertyId);
}
