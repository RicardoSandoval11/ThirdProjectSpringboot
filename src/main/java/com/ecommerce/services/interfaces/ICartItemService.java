package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Color;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;

public interface ICartItemService {
    
    CartItem getCartitemByProduct(Product product, Ram ram, Storage storage, Color color);
    void createCartItem(CartItem cartItem);
    void updatecartItem(CartItem cartItem);
    CartItem getCartItemByproductAndColor(Product product, Color color);
    List<CartItem> getCartItemsByCart(Cart cart);
    CartItem getcartItemById(Integer cartItemId);
    void deleteCartItem(CartItem cartItem);
    void deleteAllCartItems(List<CartItem> cartItems);
}
