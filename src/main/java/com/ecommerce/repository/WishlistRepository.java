package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.User;
import com.ecommerce.models.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{
    
    Optional<Wishlist> findByUser(User user);
}
