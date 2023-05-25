package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Brand;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.services.interfaces.IBrandService;

@Service
public class BrandService implements IBrandService{

    @Autowired
    private BrandRepository brandRepository;
    
    @Override
    public List<Brand> getBrandsWithOfferProducts(){
        return brandRepository.findBrandsWithOfferProducts();
    }

    @Override
    public Brand getBrandById(Integer brandId){
        Optional<Brand> optional = brandRepository.findById(brandId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    @Override
    public Page<Brand> getAllBrandsPaginated(Pageable pageable){
        return brandRepository.findAll(pageable);
    }

    @Override
    public void brandCreateUpdate(Brand brand){
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Brand brand){
        brandRepository.delete(brand);
    }

}
