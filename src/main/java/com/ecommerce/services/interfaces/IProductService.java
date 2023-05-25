package com.ecommerce.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.customObjects.overviewProduct;
import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;

public interface IProductService {
    
    Product getProductById(Integer productId);
    List<Integer> populatedCategoriesId();
    List<overviewProduct> getFeaturedOffers();
    List<Product> getFeaturedOffersAllProduct(List<Integer> ids);
    List<Product> getRecentOffers();
    Page<Product> getofferByCategory(Category category, Integer is_offer, Pageable pageable);
    Page<Product> getofferByBrand(Brand brand, Integer is_offer, Pageable pageable);
    Page<Product> getAllOffers(Integer is_offer, Pageable pageable);
    Page<Product> getOffersByPriceRange(Double lower, Double top, Pageable pageable);
    Page<Product> getOffersByRam(Integer ramId, Pageable pageable);
    Page<Product> getOffersByStorage(Integer storageId, Pageable pageable);
    Page<Product> getAllProducts(Pageable pageable);
    Page<Product> getProductsByCategory(Category category, Pageable pageable);
    Page<Product> getProductsByBrand(Brand brand, Pageable pageable);
    Page<Product> getProductsByPriceRange(Double low, Double top, Integer is_offer, Pageable pageable);
    Page<Product> getProductsByStorage(Integer storageId, Pageable pageable);
    Page<Product> getProductsByRam(Integer ramId, Pageable pageable);
    Page<Product> getProductsBykword(String kword, Pageable pageable);
    void updateProduct(Product product);
    Product saveProduct(Product product);
    Integer getOutOfStockProductsNumber();
    Page<Product> getOutOfStockProducts(Integer stock, Pageable pageable);
    void deleteProduct(Product product);
}
