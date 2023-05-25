package com.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.customObjects.BrandRequest;
import com.ecommerce.helpers.UploadFiles;
import com.ecommerce.models.Brand;
import com.ecommerce.services.interfaces.IBrandService;

@RequestMapping("/brands")
@Controller
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @Value("${ecommerceapp.path.brands}")
    private String brandImagesPath;
    
    @GetMapping("/all-brands")
    public String allBrands(Model model, Pageable pageable){
        Page<Brand> brands = brandService.getAllBrandsPaginated(pageable);
        model.addAttribute("brands", brands);
        return "brands/all_brands";
    }

    @GetMapping("/manage-brands")
    public String manageBrands(Model model, Pageable pageable){
        Page<Brand> brands = brandService.getAllBrandsPaginated(pageable);
        model.addAttribute("brands", brands);
        return "brands/manage_brands";
    }

    @GetMapping("/update-brand/{id}")
    public String updateBrand(@PathVariable("id") Integer brandId, Model model){

        Brand brand = brandService.getBrandById(brandId);

        if(brand == null){
            return "generic/not_found";
        }

        model.addAttribute("brand", brand);
        return "brands/update_brand";
    }

    @PostMapping("/save-brand")
    public String saveBrand(@ModelAttribute BrandRequest brandRequest, RedirectAttributes attributes){

        if(brandRequest.getId() != null){
            Brand brand = brandService.getBrandById(brandRequest.getId());

            brand.setName(brandRequest.getName());

            if(!brandRequest.getLogo().isEmpty()){
                String imageName = UploadFiles.saveFile(brandRequest.getLogo(), brandImagesPath);
                brand.setLogo(imageName);
            }

            brandService.brandCreateUpdate(brand);

            attributes.addFlashAttribute("msg", "Brand "+brandRequest.getName()+" has been updated successfully");
            return "redirect:/brands/manage-brands";

        }

        Brand newBrand = new Brand();

        newBrand.setName(brandRequest.getName());
        
        if(!brandRequest.getLogo().isEmpty()){
            String imageName = UploadFiles.saveFile(brandRequest.getLogo(), brandImagesPath);
            newBrand.setLogo(imageName);
        }

        brandService.brandCreateUpdate(newBrand);

        attributes.addFlashAttribute("msg", "Brand "+brandRequest.getName()+" has been created successfully");
        return "redirect:/brands/manage-brands";
    }

    @GetMapping("/create-brand")
    public String createBrand(){

        return "brands/create_brand";
    }

    @PostMapping("/delete-brand")
    public String deleteBrand(@RequestParam("brandId") Integer brandId, RedirectAttributes attributes){

        Brand brand = brandService.getBrandById(brandId);

        brandService.deleteBrand(brand);

        attributes.addFlashAttribute("msg", "Brand "+brand.getName()+" has been created successfully");
        return "redirect:/brands/manage-brands";
    }
}
