package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.customObjects.overviewProduct;
import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product getProductById(Integer productId){
        Optional<Product> optional = productRepository.findById(productId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Integer> populatedCategoriesId(){
        return productRepository.findMostPopulatedCategoriesId();
    }

    @Override
    public List<overviewProduct> getFeaturedOffers(){
        return productRepository.findFeaturedOffers();
    }

    @Override
    public List<Product> getFeaturedOffersAllProduct(List<Integer> ids){
        return productRepository.findAllById(ids);
    }

    @Override
    public List<Product> getRecentOffers(){
        return productRepository.findRecentOffers();
    }

    @Override
    public Page<Product> getofferByCategory(Category category, Integer is_offer, Pageable pageable){
        return productRepository.findByCategoryAndIsOffer(category, is_offer, pageable);
    }

    @Override
    public Page<Product> getofferByBrand(Brand brand, Integer is_offer, Pageable pageable){
        return productRepository.findByBrandAndIsOffer(brand, is_offer, pageable);
    }

    @Override
    public Page<Product> getAllOffers(Integer is_offer, Pageable pageable){
        return productRepository.findByIsOffer(is_offer, pageable);
    }

    @Override
    public Page<Product> getOffersByPriceRange(Double lower, Double top, Pageable pageable){
        return productRepository.findByOfferPriceBetweenAndIsOffer(lower, top, 1, pageable);
    }

    @Override
    public Page<Product> getOffersByRam(Integer ramId, Pageable pageable){
        List<Integer> ids = productRepository.findProductRam(ramId, 1, pageable);
        Page<Product> products = productRepository.findByIdIn(ids, pageable);
        return products;
    }

    @Override
    public Page<Product> getOffersByStorage(Integer storageId, Pageable pageable){
        List<Integer> ids = productRepository.findProductStorage(storageId, 1, pageable);
        Page<Product> products = productRepository.findByIdIn(ids, pageable);
        return products;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    } 

    @Override
    public Page<Product> getProductsByCategory(Category category, Pageable pageable){
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> getProductsByBrand(Brand brand, Pageable pageable){
        return productRepository.findByBrand(brand, pageable);
    }

    @Override
    public Page<Product> getProductsByPriceRange(Double low, Double top, Integer is_offer, Pageable pageable){
        return productRepository.findBySellPriceBetweenAndIsOffer(low, top, is_offer, pageable);
    }

    @Override
    public Page<Product> getProductsByStorage(Integer storageId, Pageable pageable){
        List<Integer> ids = productRepository.findProductStorage(storageId, 0, pageable);
        return productRepository.findByIdIn(ids, pageable);
    }

    @Override
    public Page<Product> getProductsByRam(Integer ramId, Pageable pageable){
        List<Integer> ids = productRepository.findProductRam(ramId, 0, pageable);
        return productRepository.findByIdIn(ids, pageable);
    }

    @Override
    public Page<Product> getProductsBykword(String kword, Pageable pageable){
        List<Integer> ids = productRepository.findByNameLike(kword, pageable);
        return productRepository.findByIdIn(ids, pageable);
    }

    @Override
    public void updateProduct(Product product){
        productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    public Integer getOutOfStockProductsNumber(){
        return productRepository.outOfStockProductsNumber();
    }

    @Override
    public Page<Product> getOutOfStockProducts(Integer stock, Pageable pageable){
        return productRepository.findByStock(stock, pageable);
    }

    @Override
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
}
