package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Color;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
    Optional<CartItem> findByProductAndRamAndStorageAndColor(Product product, Ram ram, Storage storage, Color color);
    Optional<CartItem> findByProductAndColor(Product product, Color color);
    List<CartItem> findByCart(Cart cart);
}
