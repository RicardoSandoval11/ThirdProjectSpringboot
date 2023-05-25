package com.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartitem", schema = "ecommercedb")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
    private Integer quantity;
    @Column(name = "sub_total")
    private Double subTotal;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId")
    private Cart cart;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ramId")
    private Ram ram;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storageId")
    private Storage storage;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colorId")
    private Color color;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Ram getRam() {
        return ram;
    }
    public void setRam(Ram ram) {
        this.ram = ram;
    }
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "CartItem [id=" + id + ", product=" + product.getId() + ", quantity=" + quantity + ", subTotal=" + subTotal
                + ", cart=" + cart.getId() + ", ram=" + ram.getId() + ", storage=" + storage.getId() + ", color=" + color.getId() + "]";
    }
    
}
