package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aao.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByGuestId(Long guestId);

    Optional<Wishlist> findByGuestIdAndPropertyId(Long guestId, Long propertyId);

    void deleteByGuestIdAndPropertyId(Long guestId, Long propertyId);
}
