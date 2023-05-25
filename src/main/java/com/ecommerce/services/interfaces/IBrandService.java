package com.ecommerce.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.models.Brand;

public interface IBrandService {
    
    List<Brand> getBrandsWithOfferProducts();
    Brand getBrandById(Integer brandId);
    List<Brand> getAllBrands();
    Page<Brand> getAllBrandsPaginated(Pageable pageable);
    void brandCreateUpdate(Brand brand);
    void deleteBrand(Brand brand);
}
