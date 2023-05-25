package com.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item", schema = "ecommercedb")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ramId")
    private Ram ram;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storageId")
    private Storage storage;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colorId")
    private Color color;
    private Integer quantity;
    @Column(name = "sub_total")
    private Double subTotal;
    private Double revenue;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private Order order;
    
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
    public Double getRevenue() {
        return revenue;
    }
    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem [id=" + id + ", product=" + product + ", ram=" + ram + ", storage=" + storage + ", color="
                + color + ", quantity=" + quantity + ", subTotal=" + subTotal + ", revenue=" + revenue + ", order="
                + order + "]";
    }
    
}
