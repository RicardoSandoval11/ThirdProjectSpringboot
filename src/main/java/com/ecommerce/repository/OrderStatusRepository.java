package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.models.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{
    
    @Query("SELECT new com.ecommerce.models.OrderStatus(os.id, os.value) FROM OrderStatus os WHERE os.id != 4")
    List<OrderStatus> findUncompletedStatus();
}
