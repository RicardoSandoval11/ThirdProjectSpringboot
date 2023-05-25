package com.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Color;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;
import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.ICartItemService;
import com.ecommerce.services.interfaces.ICartService;
import com.ecommerce.services.interfaces.IColorService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IRamService;
import com.ecommerce.services.interfaces.IStorageService;
import com.ecommerce.services.interfaces.IUserService;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IColorService colorService;

    @Autowired
    private IRamService ramService;

    @Autowired
    private IStorageService storageService;
    
    @PostMapping("/add-product")
    public String addProductToCart(
                    @RequestParam("product_id") Integer productId,
                    @RequestParam("amount") Integer amount,
                    @RequestParam(value = "color_option") Integer colorId,
                    @RequestParam(value = "storage_option", required = false) Integer storageId,
                    @RequestParam(value = "ram_option", required = false) Integer ramId, 
                    Model model, 
                    RedirectAttributes attributes, 
                    Authentication authentication){
        if(authentication == null){
            return "redirect:/";
        }

        System.out.println("==========================================");
        System.out.println(colorId);
        System.out.println(storageId);
        System.out.println(ramId);

        //  verify if user has a cartshop. If not, create one.
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Cart cart = cartService.getCartByUser(user);

        if(cart == null){
            Cart newCart = new Cart(null, user, 0.00);
            cart = cartService.CreateCart(newCart);
        }

        // validate product is not in the cart

        Product product = productService.getProductById(productId);
        CartItem cartItem;
        Ram ram;
        Storage storage;
        Color color = colorService.getColorById(colorId);
        if(product.getRam_option() == 0 && product.getStorage_option() == 0){
            cartItem = cartItemService.getCartItemByproductAndColor(product, color);
        }else{
            ram = ramService.getRamById(ramId);
            storage = storageService.getStorageById(storageId);
            cartItem = cartItemService.getCartitemByProduct(product, ram, storage, color);
        }

        if(cartItem == null){
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);

            if(colorId == null){
                color = product.getColor_options().get(0);
                newCartItem.setColor(color);
            }else{
                color = colorService.getColorById(colorId);
                newCartItem.setColor(color);
            }

            if(product.getRam_option() != 0){
                if(ramId == null){
                    ram = product.getRam_options().get(0);
                    newCartItem.setRam(ram);
                }else{
                    ram = ramService.getRamById(ramId);
                    newCartItem.setRam(ram);
                }
            }else{
                newCartItem.setRam(null);
            }
            
            if(product.getStorage_option() != 0){
                if(storageId == null){
                    storage = product.getStorage_options().get(0);
                    newCartItem.setStorage(storage);
                }else{
                    storage = storageService.getStorageById(storageId);
                    newCartItem.setStorage(storage);
                }
            }else{
                newCartItem.setStorage(null);
            }

            newCartItem.setProduct(product);
            newCartItem.setQuantity(amount);
            if(product.getIsOffer() == 1){
                newCartItem.setSubTotal(product.getOfferPrice()*amount);
            }else{
                newCartItem.setSubTotal(product.getSellPrice()*amount);
            }
            cart.setTotal(cart.getTotal() + newCartItem.getSubTotal());
            cartItemService.createCartItem(newCartItem);
            cart = cartService.UpdateCart(cart);
            attributes.addFlashAttribute("success_msg", "Product "+product.getName()+" added to your shopping cart successfully!");
            return "redirect:/cart/my-cartshop";
        }else{

            // verify if the new item to be added has the same specifications or not
            if(product.getRam_option() == 1 && product.getStorage_option() == 1){
                if(
                    (cartItem.getColor().getId() != colorId && colorId != null) || 
                    (cartItem.getStorage().getId() != storageId && storageId != null) || 
                    (cartItem.getRam().getId() != ramId && ramId != null)){
    
                        CartItem newCartItem = new CartItem();
                        newCartItem.setCart(cart);
    
                        color = colorService.getColorById(colorId);
                        newCartItem.setColor(color);
    
                        ram = ramService.getRamById(ramId);
                        newCartItem.setRam(ram);
    
                        storage = storageService.getStorageById(storageId);
                        newCartItem.setStorage(storage);
            
                        newCartItem.setProduct(product);
                        newCartItem.setQuantity(amount);
                        if(product.getIsOffer() == 1){
                            newCartItem.setSubTotal(product.getOfferPrice()*amount);
                        }else{
                            newCartItem.setSubTotal(product.getSellPrice()*amount);
                        }
                        cart.setTotal(cart.getTotal() + newCartItem.getSubTotal());
                        cartItemService.createCartItem(newCartItem);
                        cart = cartService.UpdateCart(cart);
                        attributes.addFlashAttribute("success_msg", "Product "+product.getName()+" added to your shopping cart successfully!");
                        return "redirect:/cart/my-cartshop";
    
                }else{
                    cartItem.setQuantity(cartItem.getQuantity()+amount);
                    Double oldSubTotal = cartItem.getSubTotal();
                    Double newSubTotal;
                    if(product.getIsOffer() == 0){
                        newSubTotal = cartItem.getSubTotal() + amount*product.getSellPrice();
                    }else{
                        newSubTotal = cartItem.getSubTotal() + amount*product.getOfferPrice();
                    }
                    cartItem.setSubTotal(newSubTotal);
                    cart.setTotal(cart.getTotal()+newSubTotal-oldSubTotal);
                    cartItemService.updatecartItem(cartItem);
                    cartService.UpdateCart(cart);
                    return "redirect:/cart/my-cartshop";
                }
            }else{
                cartItem.setQuantity(cartItem.getQuantity()+amount);
                Double oldSubTotal = cartItem.getSubTotal();
                Double newSubTotal;
                if(product.getIsOffer() == 0){
                    newSubTotal = cartItem.getSubTotal() + amount*product.getSellPrice();
                }else{
                    newSubTotal = cartItem.getSubTotal() + amount*product.getOfferPrice();
                }
                cartItem.setSubTotal(newSubTotal);
                cart.setTotal(cart.getTotal()+newSubTotal-oldSubTotal);
                cartItemService.updatecartItem(cartItem);
                cartService.UpdateCart(cart);
                return "redirect:/cart/my-cartshop";
            }

        }

    }

    @GetMapping("/my-cartshop")
    public String userCart(Authentication authentication, Model model){
        
        // find user
        User user = userService.getUserByEmail(authentication.getName());

        // find user's cart
        Cart cart = cartService.getCartByUser(user);

        if(cart == null){
            // create an empty cart
            Cart newCart = new Cart(null, user, 0.00);
            cart = cartService.CreateCart(newCart);
        }

        // find cart items
        List<CartItem> cartItems = cartItemService.getCartItemsByCart(cart);
        
        model.addAttribute("cart", cart);
        model.addAttribute("cart_items", cartItems);

        return "cart/my_cart";
    }

    @PostMapping("/remove-product")
    public String removeItemFromCart(@RequestParam("cartitem_id") Integer cartItemId){

        CartItem cartItem = cartItemService.getcartItemById(cartItemId);

        // get cart
        Cart cart = cartService.getCartById(cartItem.getCart().getId());

        if(cartItem.getQuantity() - 1 == 0){
            if(cartItem.getProduct().getIsOffer() == 1){
                Double newTotalCart = cart.getTotal() - cartItem.getProduct().getOfferPrice();
                cart.setTotal(newTotalCart);
                cartService.UpdateCart(cart);
            }else{
                Double newTotalCart = cart.getTotal() - cartItem.getProduct().getSellPrice();
                cart.setTotal(newTotalCart);
                cartService.UpdateCart(cart);
            }
            // delete cart item
            cartItemService.deleteCartItem(cartItem);
            return "redirect:/cart/my-cartshop";
        }

        // just decrease the amount of the product
        Integer currentProductQuatity = cartItem.getQuantity();
        Integer newProductQuantity = currentProductQuatity - 1;

        cartItem.setQuantity(newProductQuantity);
        
        if(cartItem.getProduct().getIsOffer() == 1){
            Double newSubTotal = currentProductQuatity*cartItem.getProduct().getOfferPrice() - cartItem.getProduct().getOfferPrice();
            cartItem.setSubTotal(newSubTotal);

            // update total cart
            Double newTotalCart = cart.getTotal() - cartItem.getProduct().getOfferPrice();
            cart.setTotal(newTotalCart);
        }else{
            Double newSubTotal = currentProductQuatity*cartItem.getProduct().getSellPrice() - cartItem.getProduct().getSellPrice();
            cartItem.setSubTotal(newSubTotal);
             // update total cart
             Double newTotalCart = cart.getTotal() - cartItem.getProduct().getSellPrice();
             cart.setTotal(newTotalCart);
        }

        cartItemService.updatecartItem(cartItem);
        cartService.UpdateCart(cart);
        
        return "redirect:/cart/my-cartshop";
    }

}
