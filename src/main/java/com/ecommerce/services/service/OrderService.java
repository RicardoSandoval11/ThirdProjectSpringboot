package com.ecommerce.services.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ecommerce.customObjects.orderDateObject;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderStatus;
import com.ecommerce.models.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.services.interfaces.IOrderService;

@Service
public class OrderService implements IOrderService{
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order){
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Override
    public void updateOrder(Order order){
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer orderId){
        Optional<Order> optional = orderRepository.findById(orderId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<Order> getOrdersByUser(User user, Pageable pageable){
        return orderRepository.findByUser(user, pageable);
    }

    @Override
    public Integer getNumberOfPendingOrders(){
        return orderRepository.findNumberOfPendingOrders();
    }

    @Override
    public Integer getNumberOfOrders(Date startDate, Date endDate){
        return orderRepository.salesOfMonth(startDate, endDate);
    }

    @Override
    public List<orderDateObject> getOrdersOfMonth(Date startDate, Date endDate){
        return orderRepository.getOrdersOfMonth(startDate, endDate);
    }

    @Override
    public List<Order> getRecentOrders(){
        return orderRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable){
        return orderRepository.findByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Order> getPendingOrders(OrderStatus orderStatus,Pageable pageable){
        return orderRepository.findByOrderStatusNotOrderByCreatedAtAsc(orderStatus, pageable);
    }

    @Override
    public Page<Order> getPendingOrdersByStatus(OrderStatus orderStatus, Pageable pageable){
        return orderRepository.findByOrderStatusOrderByCreatedAtAsc(orderStatus, pageable);
    }
}
