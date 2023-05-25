package com.ecommerce.services.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.models.Product;
import com.ecommerce.models.Wishlist;
import com.ecommerce.models.WishlistItem;

public interface IWishlistItemService {
    
    Page<WishlistItem> getItemsByWishlist(Wishlist wishlist, Pageable pageable);
    void CreateUpdateWishlistItem(WishlistItem wishlistItem);
    void deleteWishlistItem(WishlistItem wishlistItem);
    WishlistItem getItemByProductAndWishlist(Product product, Wishlist wishlist);
    WishlistItem getItemById(Integer itemId);
}
