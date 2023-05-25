package com.ecommerce.models;

import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_statuses", schema = "ecommercedb")
public class OrderStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;

    public OrderStatus(){}

    public OrderStatus(@Qualifier("id") Integer id, @Qualifier("value") String value){
        this.id = id;
        this.value = value;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OrderStatus [id=" + id + ", value=" + value + "]";
    }
    
}
