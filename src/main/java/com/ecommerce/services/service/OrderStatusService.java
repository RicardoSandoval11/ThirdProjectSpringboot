package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.OrderStatus;
import com.ecommerce.repository.OrderStatusRepository;
import com.ecommerce.services.interfaces.IOrderStatusService;

@Service
public class OrderStatusService implements IOrderStatusService{

    @Autowired
    private OrderStatusRepository orderStatusRepository;
    
    @Override
    public OrderStatus getStatusById(Integer statusId){
        Optional<OrderStatus> optional = orderStatusRepository.findById(statusId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<OrderStatus> getUncompletedStatuses(){
        return orderStatusRepository.findUncompletedStatus();
    }

    @Override
    public List<OrderStatus> getAllStatuses(){
        return orderStatusRepository.findAll();
    }
}
