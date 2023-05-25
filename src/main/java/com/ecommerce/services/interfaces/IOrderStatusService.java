package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.OrderStatus;

public interface IOrderStatusService {
    
    OrderStatus getStatusById(Integer statusId);
    List<OrderStatus> getUncompletedStatuses();
    List<OrderStatus> getAllStatuses();
}
