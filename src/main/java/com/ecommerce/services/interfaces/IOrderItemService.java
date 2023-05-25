package com.ecommerce.services.interfaces;

import java.util.Date;
import java.util.List;

import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;

public interface IOrderItemService {
    
    void saveAllOrderItems(List<OrderItem> orderItems);
    List<OrderItem> getOrderItemsByOrder(Order order);
    Double monthRevenue(Date startDate, Date endDate);
}
