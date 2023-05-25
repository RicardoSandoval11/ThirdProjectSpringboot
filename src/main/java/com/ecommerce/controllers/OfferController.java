package com.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;
import com.ecommerce.services.interfaces.IBrandService;
import com.ecommerce.services.interfaces.ICategoryService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IRamService;
import com.ecommerce.services.interfaces.IStorageService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IRamService ramService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private IProductService productService;
    
    @GetMapping("/all-offers")
    public String allOffers(HttpServletRequest request, Model model, Pageable pageable){

        // find required data
        List<Category> offerProductCategories = categoryService.getCategoriesWithOfferProducts();
        List<Brand> offerProductBrands = brandService.getBrandsWithOfferProducts();
        List<Ram> offerProductRam = ramService.getRamOfferProducts();
        List<Storage> offerProductStorage = storageService.getStoragesWithOfferProducts();

        // add data to the template
        model.addAttribute("categories", offerProductCategories);
        model.addAttribute("brands", offerProductBrands);
        model.addAttribute("rams", offerProductRam);
        model.addAttribute("storages", offerProductStorage);

        String value = request.getParameter("value");
        String filterBy = request.getParameter("filter_by");

        if(filterBy != null && value != null){
            model.addAttribute("filter_by", filterBy);
            model.addAttribute("value", value);
            if (filterBy.equals("category")) {
                Category category = categoryService.getCategoryById(Integer.parseInt(value));
                Page<Product> products = productService.getofferByCategory(category, 1, pageable);
    
                model.addAttribute("offers", products);
                return "offers/all_offers";
            }else if(filterBy.equals("brand")){
                Brand brand = brandService.getBrandById(Integer.parseInt(value));
                Page<Product> products = productService.getofferByBrand(brand, 1, pageable);
                model.addAttribute("offers", products);
                return "offers/all_offers";
            }else if(filterBy.equals("price")){
                Integer lowIndex = value.indexOf("to");
                Integer topIndex = lowIndex + 2;
                String lowerStr = value.substring(0, lowIndex);
                String topStr = value.substring(topIndex, value.length());
                Double lower = Double.parseDouble(lowerStr);
                Double top = Double.parseDouble(topStr);
                Page<Product> products = productService.getOffersByPriceRange(lower, top, pageable);
                model.addAttribute("offers", products);
                return "offers/all_offers";
            }else if(filterBy.equals("ram")){
                Integer ramId = Integer.parseInt(value);
                Page<Product> products = productService.getOffersByRam(ramId, pageable);
                model.addAttribute("offers", products);
                return "offers/all_offers";
            }else if(filterBy.equals("storage")){
                Integer storageId = Integer.parseInt(value);
                Page<Product> products = productService.getOffersByStorage(storageId, pageable);
                model.addAttribute("offers", products);
                return "offers/all_offers";
            }
        }
        Page<Product> products = productService.getAllOffers(1, pageable);
        model.addAttribute("offers", products);
        return "offers/all_offers";
    }
}
