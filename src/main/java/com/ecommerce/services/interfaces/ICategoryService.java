package com.ecommerce.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.models.Category;

public interface ICategoryService {

    List<Category> getMostPopulatedCategories(List<Integer> ids);
    List<Category> getCategoriesWithOfferProducts();
    Category getCategoryById(Integer categoryId);
    List<Category> getAllCategories();
    Page<Category> getAllCategoriesPaginated(Pageable pageable);
    void categoryUpdateSave(Category category);
    void deleteCategory(Category category);
}
