package com.ecommerce.customObjects;

import org.springframework.web.multipart.MultipartFile;

public class CategoryRequest {
    
    private Integer id;
    private String name;
    private String description;
    private MultipartFile image;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
                + "]";
    }
    
}

