package com.ecommerce.services.interfaces;

import com.ecommerce.models.User;
import com.ecommerce.models.Wishlist;

public interface IWishlistService {
    
    Wishlist getWishlistByUser(User user);
    Wishlist createWishlist(Wishlist wishlist);
    void updateWishlist(Wishlist wishlist);
    
}
