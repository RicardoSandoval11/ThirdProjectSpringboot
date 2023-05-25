package com.ecommerce.services.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Product;
import com.ecommerce.models.Wishlist;
import com.ecommerce.models.WishlistItem;
import com.ecommerce.repository.WishlistItemRepository;
import com.ecommerce.services.interfaces.IWishlistItemService;

@Service
public class WishlistItemService implements IWishlistItemService{

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Override
    public Page<WishlistItem> getItemsByWishlist(Wishlist wishlist, Pageable pageable) {
        return wishlistItemRepository.findByWishlist(wishlist, pageable);
    }

    @Override
    public void CreateUpdateWishlistItem(WishlistItem wishlistItem) {
        wishlistItemRepository.save(wishlistItem);
    }

    @Override
    public void deleteWishlistItem(WishlistItem wishlistItem) {
        wishlistItemRepository.delete(wishlistItem);
    }
    
    @Override
    public WishlistItem getItemByProductAndWishlist(Product product, Wishlist wishlist){
        Optional<WishlistItem> optional = wishlistItemRepository.findByProductAndWishlist(product, wishlist);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public WishlistItem getItemById(Integer itemId){
        Optional<WishlistItem> optional = wishlistItemRepository.findById(itemId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
