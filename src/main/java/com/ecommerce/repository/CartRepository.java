package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.Cart;
import com.ecommerce.models.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    
    Optional<Cart> findByUser(User user);
}
