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

import com.ecommerce.customObjects.CategoryRequest;
import com.ecommerce.helpers.UploadFiles;
import com.ecommerce.models.Category;
import com.ecommerce.services.interfaces.ICategoryService;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private ICategoryService categoryService;

    @Value("${ecommerceapp.path.categories}")
    public String categoryImagePath;
    
    @GetMapping("/all-categories")
    public String allCategories(Model model, Pageable pageable){
        Page<Category> categories = categoryService.getAllCategoriesPaginated(pageable);
        model.addAttribute("categories", categories);
        return "categories/all_categories";
    }

    @GetMapping("/manage-categories")
    public String manageCategories(Model model, Pageable pageable){
        Page<Category> categories = categoryService.getAllCategoriesPaginated(pageable);
        model.addAttribute("categories", categories);
        return "categories/manage_categories";
    }

    @GetMapping("/update-category/{id}")
    public String updateCategory(@PathVariable("id") Integer categoryId, Model model){

        Category category = categoryService.getCategoryById(categoryId);

        if(category == null){
            return "generic/not_found";
        }

        model.addAttribute("category", category);

        return "categories/update_category";
    }

    @GetMapping("/create-category")
    public String createCategory(){
        return "categories/create_category";
    }

    @PostMapping("/save-category")
    public String saveChanges(@ModelAttribute CategoryRequest categoryRequest, RedirectAttributes attributes){

        // verify if an update or creation is required
        if(categoryRequest.getId() != null){
            Category category = categoryService.getCategoryById(categoryRequest.getId());

            // update fields
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            
            if(!categoryRequest.getImage().isEmpty()){
                String imageName = UploadFiles.saveFile(categoryRequest.getImage(), categoryImagePath);
                category.setImage(imageName);
            }

            categoryService.categoryUpdateSave(category);

            attributes.addFlashAttribute("msg", "Category "+categoryRequest.getName()+" has been updated successfully");
            return "redirect:/categories/manage-categories";
        }

        Category newCategory = new Category();

        newCategory.setName(categoryRequest.getName());
        newCategory.setDescription(categoryRequest.getDescription());
        
        if(!categoryRequest.getImage().isEmpty()){
            String imageName = UploadFiles.saveFile(categoryRequest.getImage(), categoryImagePath);
            newCategory.setImage(imageName);
        }

        categoryService.categoryUpdateSave(newCategory);
        attributes.addFlashAttribute("msg", "Category "+categoryRequest.getName()+" has been created successfully");
        return "redirect:/categories/manage-categories";
    }

    @PostMapping("/delete-category")
    public String deleteCategory(@RequestParam("categoryId") Integer categoryId, RedirectAttributes attributes){

        Category category = categoryService.getCategoryById(categoryId);

        categoryService.deleteCategory(category);

        attributes.addFlashAttribute("msg", "Category "+category.getName()+" has been removed successfully");
        return "redirect:/categories/manage-categories";
    }
}
