package com.ecommerce.services.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.services.interfaces.IOrderItemService;

@Service
public class OrderitemService implements IOrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void saveAllOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrder(Order order){
        return orderItemRepository.findByOrder(order);
    }

    @Override
    public Double monthRevenue(Date startDate, Date endDate){
        return orderItemRepository.findRevenueOfDateRange(startDate, endDate);
    }
    
}
