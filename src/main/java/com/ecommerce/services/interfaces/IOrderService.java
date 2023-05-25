package com.ecommerce.services.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.customObjects.orderDateObject;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderStatus;
import com.ecommerce.models.User;

public interface IOrderService {
    
    Order createOrder(Order order);
    void updateOrder(Order order);
    Order getOrderById(Integer orderId);
    Page<Order> getOrdersByUser(User user, Pageable pageable);
    Integer getNumberOfPendingOrders();
    Integer getNumberOfOrders(Date startDate, Date endDate);
    List<orderDateObject> getOrdersOfMonth(Date startDate, Date endDate);
    List<Order> getRecentOrders();
    Page<Order> getAllOrders(Pageable pageable);
    Page<Order> getPendingOrders(OrderStatus orderStatus,Pageable pageable);
    Page<Order> getPendingOrdersByStatus(OrderStatus orderStatus, Pageable pageable);
}
