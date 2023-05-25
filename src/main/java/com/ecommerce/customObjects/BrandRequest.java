package com.ecommerce.customObjects;

import org.springframework.web.multipart.MultipartFile;

public class BrandRequest {

    private Integer id;
    private String name;
    private MultipartFile logo;

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
    public MultipartFile getLogo() {
        return logo;
    }
    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + "]";
    }

}
