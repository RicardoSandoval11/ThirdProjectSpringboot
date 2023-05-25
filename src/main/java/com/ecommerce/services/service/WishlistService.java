package com.ecommerce.services.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.User;
import com.ecommerce.models.Wishlist;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.services.interfaces.IWishlistService;

@Service
public class WishlistService implements IWishlistService{

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist getWishlistByUser(User user) {
        Optional<Wishlist> optional = wishlistRepository.findByUser(user);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        Wishlist createdWishlist = wishlistRepository.save(wishlist);
        return createdWishlist;
    }

    @Override
    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    
    
}
