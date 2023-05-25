package com.ecommerce.customObjects;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;

public class orderDateObject {
    
    private Integer id;
    private Date createdAt;
    private Double orderRevenue;

    public orderDateObject(@Qualifier("id") Integer id, @Qualifier("createdAt") Date createdAt, @Qualifier("orderRevenue") Double orderRevenue){
        this.id = id;
        this.createdAt = createdAt;
        this.orderRevenue = orderRevenue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getOrderRevenue() {
        return orderRevenue;
    }

    public void setOrderRevenue(Double orderRevenue) {
        this.orderRevenue = orderRevenue;
    }

    @Override
    public String toString() {
        return "orderDateObject [id=" + id + ", createdAt=" + createdAt + ", orderRevenue=" + orderRevenue + "]";
    }
    
}
