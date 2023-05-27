package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.services.interfaces.IAdvertisementService;
import com.ecommerce.services.interfaces.ICategoryService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.customObjects.overviewProduct;
import com.ecommerce.models.Advertisement;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;

@Controller
public class HomeController {

    @Autowired
    private IAdvertisementService advertisementService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;
    
    @GetMapping("/")
    public String homeView(Model model){

        List<Advertisement> advertisements = advertisementService.getMostRecentAdvertisement();
        List<Integer> categoryIds = productService.populatedCategoriesId();
        List<Category> featured_categories = categoryService.getMostPopulatedCategories(categoryIds);
        List<overviewProduct> product_offers = productService.getFeaturedOffers();
        List<Integer> productIds = new ArrayList<Integer>();
        for (overviewProduct overviewProduct : product_offers) {
            productIds.add(overviewProduct.getId());
        }
        List<Product> featured_offers = productService.getFeaturedOffersAllProduct(productIds);
        List<Product> recent_products = productService.getRecentOffers();

        model.addAttribute("advertisements", advertisements);
        model.addAttribute("featured_categories", featured_categories);
        model.addAttribute("featured_offers", featured_offers);
        model.addAttribute("recent_products", recent_products);

        return "home/home";
    }
}
