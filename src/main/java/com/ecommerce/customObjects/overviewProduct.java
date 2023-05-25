package com.ecommerce.customObjects;

import org.springframework.beans.factory.annotation.Qualifier;

public class overviewProduct {
    
    private Integer id;
    private String image1;
    private String name;
    private Double sellPrice;
    private Double offerPrice;

    public overviewProduct(
                @Qualifier("id") Integer id, 
                @Qualifier("image1") String image1, 
                @Qualifier("name") String name, 
                @Qualifier("sellPrice") Double sellPrice, 
                @Qualifier("offerPrice") Double offerPrice) {
        super();
        this.id = id;
        this.image1 = image1;
        this.name = name;
        this.sellPrice = sellPrice;
        this.offerPrice = offerPrice;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getImage1() {
        return image1;
    }
    public void setImage1(String image1) {
        this.image1 = image1;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }
    public Double getOfferPrice() {
        return offerPrice;
    }
    public void setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
    }

    @Override
    public String toString() {
        return "overviewProduct [id=" + id + ", image1=" + image1 + ", name=" + name + ", sellPrice=" + sellPrice
                + ", offerPrice=" + offerPrice + "]";
    }

    
}
