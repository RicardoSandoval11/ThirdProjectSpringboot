package com.ecommerce.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand", schema = "ecommercedb")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String logo;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    List<Product> products;

    public Brand(){}
    
    public Brand(@Qualifier("id") Integer id, @Qualifier("name") String name, @Qualifier("logo") String logo) {
        super();
        this.id = id;
        this.name = name;
        this.logo = logo;
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
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + ", products=" + products + "]";
    }
    
}
