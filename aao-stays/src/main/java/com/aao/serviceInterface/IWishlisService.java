package com.aao.serviceInterface;

import com.aao.dto.*;

import java.util.List;

public interface IWishlistService {

    WishlistResponseDto addToWishlist(WishlistCreateRequestDto request);

    List<WishlistResponseDto> getWishlistByGuest(Long guestId);

    void removeFromWishlist(Long guestId, Long propertyId);
}
