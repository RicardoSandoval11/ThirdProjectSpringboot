package com.ecommerce.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product", schema = "ecommercedb")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double purchase_price;
    @Column(name = "sell_price")
    private Double sellPrice;
    @Column(name = "is_offer")
    private Integer isOffer;
    private Integer ram_option;
    private Integer storage_option;
    @Column(name = "offer_price")
    private Double offerPrice;
    private String specifications;
    private Integer stock;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brandId")
    private Brand brand;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_ram", joinColumns = @JoinColumn(name="productId"), inverseJoinColumns = @JoinColumn(name="ramId"))
    private List<Ram> ram_options;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_storage", joinColumns = @JoinColumn(name="productId"), inverseJoinColumns = @JoinColumn(name="storageId"))
    private List<Storage> storage_options;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_colors", joinColumns = @JoinColumn(name="productId"), inverseJoinColumns = @JoinColumn(name="colorId"))
    private List<Color> color_options;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public Product(){}
    
    public Product(Integer id, String name, Double sellPrice, Integer isOffer, Double offerPrice, Integer stock,
            String image1) {
        super();
        this.id = id;
        this.name = name;
        this.sellPrice = sellPrice;
        this.isOffer = isOffer;
        this.offerPrice = offerPrice;
        this.stock = stock;
        this.image1 = image1;
    }

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

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
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
