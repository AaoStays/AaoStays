package com.aao.controller;

import com.aao.dto.*;
import com.aao.serviceInterface.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final IWishlistService wishlistService;

    // Add to wishlist
    @PostMapping
    public ResponseEntity<WishlistResponseDto> addToWishlist(
            @RequestBody WishlistCreateRequestDto request
    ) {
        return ResponseEntity.ok(wishlistService.addToWishlist(request));

    
    }

    // Get wishlist by guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<WishlistResponseDto>> getWishlist(
            @PathVariable Long guestId
    ) {
        return ResponseEntity.ok(wishlistService.getWishlistByGuest(guestId));
    }

    // Remove from wishlist
    @DeleteMapping
    public ResponseEntity<Void> removeFromWishlist(
            @RequestParam Long guestId,
            @RequestParam Long propertyId
    ) {
        wishlistService.removeFromWishlist(guestId, propertyId);
        return ResponseEntity.noContent().build();
    }
}
