package com.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.models.Product;
import com.ecommerce.models.User;
import com.ecommerce.models.Wishlist;
import com.ecommerce.models.WishlistItem;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IUserService;
import com.ecommerce.services.interfaces.IWishlistItemService;
import com.ecommerce.services.interfaces.IWishlistService;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IWishlistItemService wishlistItemService;

    @Autowired
    private IWishlistService wishlistService;

    @PostMapping("/add-product")
    public String addProduct(@RequestParam("productId") Integer productId, Authentication authentication, RedirectAttributes attributes){

        // verify if user is authenticated
        if(authentication == null){
            return "redirect:/";
        }

        // verify if user already has the product in the wishlist
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        
        Wishlist userWishlist = wishlistService.getWishlistByUser(user);

        if(userWishlist == null){
            Wishlist newWishlist = new Wishlist();
            newWishlist.setUser(user);

            userWishlist = wishlistService.createWishlist(newWishlist);
        }

        Product product = productService.getProductById(productId);

        WishlistItem wishlistItem = wishlistItemService.getItemByProductAndWishlist(product, userWishlist);

        if(wishlistItem != null){
            return "redirect:/wishlist/my-wishlist";
        }

        WishlistItem newWishlistItem = new WishlistItem();
        newWishlistItem.setProduct(product);
        newWishlistItem.setWishlist(userWishlist);
        wishlistItemService.CreateUpdateWishlistItem(newWishlistItem);

        attributes.addFlashAttribute("success_msg", "The product "+product.getName()+" has been added successfully");
        return "redirect:/wishlist/my-wishlist";
    }

    @GetMapping("/my-wishlist")
    public String userWushlist(Authentication authentication, Model model, Pageable pageable){

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Wishlist wishlist = wishlistService.getWishlistByUser(user);

        if(wishlist == null){
            return "wishlist/user_wishlist";
        }

        Page<WishlistItem> wishlistItems = wishlistItemService.getItemsByWishlist(wishlist, pageable);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishlistItems", wishlistItems);
        return "wishlist/user_wishlist";
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam("wishlistItemId") Integer itemId, RedirectAttributes attributes){

        WishlistItem wishlistItem = wishlistItemService.getItemById(itemId);

        if(wishlistItem == null){
            return "redirect:/wishlist/my-wishlist";
        }

        wishlistItemService.deleteWishlistItem(wishlistItem);

        return "redirect:/wishlist/my-wishlist";
    }

}
