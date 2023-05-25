package com.ecommerce.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
    
    List<OrderItem> findByOrder(Order order);

    @Query("SELECT SUM(oi.revenue) FROM OrderItem oi INNER JOIN Order o ON o.id = oi.order.id WHERE o.createdAt between :lowerDate AND :topDate")
    Double findRevenueOfDateRange(@Param("lowerDate") Date lowerDate, @Param("topDate") Date topDate);
}
