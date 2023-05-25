package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query("SELECT new com.ecommerce.models.Category(c.id,c.name,c.description,c.image) FROM Category c WHERE c.id IN :ids")
    List<Category> findByIdIn(@Param("ids") List<Integer> ids);

    @Query("SELECT new com.ecommerce.models.Category(c.id,c.name,c.description,c.image) FROM Category c INNER JOIN Product p ON p.category.id = c.id WHERE p.isOffer = 1 GROUP BY (p.category.id) ORDER BY COUNT(p.id) DESC LIMIT 8")
    List<Category> findCategoriesWithOfferProducts();

}
