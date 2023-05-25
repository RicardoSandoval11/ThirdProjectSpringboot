package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<Category> getMostPopulatedCategories(List<Integer> ids){
        return categoryRepository.findByIdIn(ids);
    }

    @Override
    public List<Category> getCategoriesWithOfferProducts(){
        return categoryRepository.findCategoriesWithOfferProducts();
    }

    @Override
    public Category getCategoryById(Integer categoryId){
        Optional<Category> optional = categoryRepository.findById(categoryId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategoriesPaginated(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void categoryUpdateSave(Category category){
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category){
        categoryRepository.delete(category);
    }
}
