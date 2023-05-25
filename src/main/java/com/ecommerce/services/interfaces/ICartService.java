package com.ecommerce.services.interfaces;

import com.ecommerce.models.Cart;
import com.ecommerce.models.User;

public interface ICartService {
    
    Cart getCartByUser(User user);
    Cart CreateCart(Cart cart);
    Cart UpdateCart(Cart cart);
    Cart getCartById(Integer cartItemId);
}
