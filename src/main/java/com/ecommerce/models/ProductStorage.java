package com.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_storage", schema = "ecommercedb")
public class ProductStorage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Integer storageId;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getStorageId() {
        return storageId;
    }
    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    @Override
    public String toString() {
        return "ProductStorage [id=" + id + ", productId=" + productId + ", storageId=" + storageId + "]";
    }
    
}
