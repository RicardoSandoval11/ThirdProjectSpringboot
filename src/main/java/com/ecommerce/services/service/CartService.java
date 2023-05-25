package com.ecommerce.services.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Cart;
import com.ecommerce.models.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.services.interfaces.ICartService;

@Service
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public Cart getCartByUser(User user){
        Optional<Cart> optional = cartRepository.findByUser(user);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Cart CreateCart(Cart cart){
        Cart createdCart = cartRepository.save(cart);
        return createdCart;
    }

    @Override
    public Cart UpdateCart(Cart cart){
        Cart updatedCart = cartRepository.save(cart);
        return updatedCart;
    }

    @Override
    public Cart getCartById(Integer cartItemId){
        Optional<Cart> optional = cartRepository.findById(cartItemId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
