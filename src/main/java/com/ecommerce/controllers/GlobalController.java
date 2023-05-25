package com.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.services.interfaces.IBrandService;
import com.ecommerce.services.interfaces.ICategoryService;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBrandService brandService;
    
    @ModelAttribute("global_categories")
    public List<Category> globalCategories(){
        return categoryService.getAllCategories();
    }

    @ModelAttribute("global_brands")
    public List<Brand> globalBrands(){
        return brandService.getAllBrands();
    }

    @ModelAttribute("global_popular_categories")
    public List<Category> globalPopularCategories(){
        return categoryService.getCategoriesWithOfferProducts().subList(0, 3);
    }
}
