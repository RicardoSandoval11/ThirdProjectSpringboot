package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.customObjects.overviewProduct;
import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    @Query("SELECT p.category.id FROM Product p GROUP BY (p.category.id) ORDER BY COUNT(p.id) DESC LIMIT 12")
    List<Integer> findMostPopulatedCategoriesId();

    @Query("SELECT new com.ecommerce.customObjects.overviewProduct(id, image1, name, sellPrice, offerPrice) FROM Product WHERE isOffer = 1 ORDER BY ((sellPrice-offerPrice)/sellPrice) DESC LIMIT 4")
    List<overviewProduct> findFeaturedOffers();

    @Query("SELECT new com.ecommerce.models.Product(p.id, p.name, p.sellPrice, p.isOffer, p.offerPrice, p.stock, p.image1) FROM Product p WHERE isOffer = 1 ORDER BY id DESC LIMIT 4")
    List<Product> findRecentOffers();

    /* Filter offers */
    Page<Product> findByCategoryAndIsOffer(Category category, Integer is_offer, Pageable pageable);

    Page<Product> findByBrandAndIsOffer(Brand brand, Integer is_offer,Pageable pageable);

    Page<Product> findByOfferPriceBetweenAndIsOffer(Double lower, Double upper, Integer is_offer, Pageable pageable);

    @Query("SELECT p.id FROM Product p INNER JOIN ProductRam pr ON pr.productId = p.id INNER JOIN Ram r ON r.id = pr.ramId WHERE r.id = :ramId AND p.isOffer = :isOffer")
    List<Integer> findProductRam(@Param("ramId") Integer ramId, @Param("isOffer") Integer is_offer, Pageable pageable);

    @Query("SELECT p.id FROM Product p INNER JOIN ProductStorage ps ON ps.productId = p.id INNER JOIN Storage s ON s.id = ps.storageId WHERE s.id = :storageId AND p.isOffer = :isOffer")
    List<Integer> findProductStorage(@Param("storageId") Integer storageId, @Param("isOffer") Integer is_offer, Pageable pageable);

    Page<Product> findByIdIn(List<Integer> ids, Pageable pageable);

    Page<Product> findByIsOffer(Integer is_offer, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByBrand(Brand brand, Pageable pageable);
    Page<Product> findBySellPriceBetweenAndIsOffer(Double lower, Double top, Integer is_offer ,Pageable pageable);

    @Query("SELECT p.id FROM Product p WHERE p.name LIKE %:kword%")
    List<Integer> findByNameLike(@Param("kword") String kword, Pageable pageable);

    @Query("SELECT COUNT(p.id) FROM Product p WHERE p.stock = 0")
    Integer outOfStockProductsNumber();

    Page<Product> findByStock(Integer stock, Pageable pageable);

}


    


