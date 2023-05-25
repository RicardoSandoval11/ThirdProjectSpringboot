package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.Product;
import com.ecommerce.models.Wishlist;
import com.ecommerce.models.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer>{
    
    Page<WishlistItem> findByWishlist(Wishlist wishlist, Pageable pageable);
    Optional<WishlistItem> findByProductAndWishlist(Product product, Wishlist wishlist);
}
