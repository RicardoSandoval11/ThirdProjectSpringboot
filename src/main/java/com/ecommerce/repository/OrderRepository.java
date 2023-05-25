package com.ecommerce.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.customObjects.orderDateObject;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderStatus;
import com.ecommerce.models.User;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    
    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findByOrderStatusIn(List<OrderStatus> orderStatuses, Pageable pageable);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE o.orderStatus.id != 4")
    Integer findNumberOfPendingOrders();

    @Query("SELECT COUNT(o.id) FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    Integer salesOfMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT new com.ecommerce.customObjects.orderDateObject(o.id, o.createdAt, SUM(oi.revenue)) FROM Order o INNER JOIN OrderItem oi ON oi.order.id = o.id WHERE o.createdAt BETWEEN :startDate AND :endDate GROUP BY (oi.order.id)")
    List<orderDateObject> getOrdersOfMonth(@Param("startDate") Date starDate, @Param("endDate") Date endDate);

    List<Order> findTop5ByOrderByCreatedAtDesc();

    Page<Order> findByOrderByCreatedAtDesc(Pageable pageable);

    Page<Order> findByOrderStatusOrderByCreatedAtAsc(OrderStatus orderStatus, Pageable pageable);

    Page<Order> findByOrderStatusNotOrderByCreatedAtAsc(OrderStatus orderStatus, Pageable pageable);

}
