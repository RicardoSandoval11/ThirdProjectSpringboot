package com.ecommerce.customObjects;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.models.Brand;
import com.ecommerce.models.Category;
import com.ecommerce.models.Color;
import com.ecommerce.models.Ram;
import com.ecommerce.models.Storage;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double purchase_price;
    private Double sellPrice;
    private Integer isOffer;
    private Integer ram_option;
    private Integer storage_option;
    private Double offerPrice;
    private String specifications;
    private Integer stock;
    private Category category;
    private Brand brand;
    private List<Ram> ram_options;
    private List<Storage> storage_options;
    private List<Color> color_options;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private MultipartFile image4;

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

    public Double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(Double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public Integer getIsOffer() {
        return isOffer;
    }

    public void setIsOffer(Integer isOffer) {
        this.isOffer = isOffer;
    }

    public Integer getRam_option() {
        return ram_option;
    }

    public void setRam_option(Integer ram_option) {
        this.ram_option = ram_option;
    }

    public Integer getStorage_option() {
        return storage_option;
    }

    public void setStorage_option(Integer storage_option) {
        this.storage_option = storage_option;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Ram> getRam_options() {
        return ram_options;
    }

    public void setRam_options(List<Ram> ram_options) {
        this.ram_options = ram_options;
    }

    public List<Storage> getStorage_options() {
        return storage_options;
    }

    public void setStorage_options(List<Storage> storage_options) {
        this.storage_options = storage_options;
    }

    public List<Color> getColor_options() {
        return color_options;
    }

    public void setColor_options(List<Color> color_options) {
        this.color_options = color_options;
    }

    public MultipartFile getImage1() {
        return image1;
    }

    public void setImage1(MultipartFile image1) {
        this.image1 = image1;
    }

    public MultipartFile getImage2() {
        return image2;
    }

    public void setImage2(MultipartFile image2) {
        this.image2 = image2;
    }

    public MultipartFile getImage3() {
        return image3;
    }

    public void setImage3(MultipartFile image3) {
        this.image3 = image3;
    }

    public MultipartFile getImage4() {
        return image4;
    }

    public void setImage4(MultipartFile image4) {
        this.image4 = image4;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name +"]";
    }

}
