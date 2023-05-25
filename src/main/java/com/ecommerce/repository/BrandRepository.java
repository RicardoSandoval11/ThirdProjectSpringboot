package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
    
    @Query("SELECT new com.ecommerce.models.Brand(b.id, b.name, b.logo) FROM Brand b INNER JOIN Product p ON p.brand.id = b.id WHERE p.isOffer=1 GROUP BY (b.id)")
    List<Brand> findBrandsWithOfferProducts();
}
