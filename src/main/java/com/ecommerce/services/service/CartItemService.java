package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Color;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.services.interfaces.ICartItemService;

@Service
public class CartItemService implements ICartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Override
    public CartItem getCartitemByProduct(Product product, Ram ram, Storage storage, Color color){
        Optional<CartItem> optional = cartItemRepository.findByProductAndRamAndStorageAndColor(product, ram, storage, color);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void createCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updatecartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItemByproductAndColor(Product product, Color color){
        Optional<CartItem> optional = cartItemRepository.findByProductAndColor(product, color);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCart(Cart cart){
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public CartItem getcartItemById(Integer cartItemId){
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void deleteCartItem(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteAllCartItems(List<CartItem> cartItems){
        cartItemRepository.deleteAll(cartItems);
    }
}
