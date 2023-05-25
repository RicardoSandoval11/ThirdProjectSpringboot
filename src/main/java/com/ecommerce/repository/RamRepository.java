package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.models.Ram;

public interface RamRepository extends JpaRepository<Ram, Integer>{
    
    @Query("SELECT new com.ecommerce.models.Ram(r.id, r.value) FROM Ram r inner join ProductRam pr on r.id = pr.ramId inner join Product p on p.id = pr.productId where p.isOffer = 1 and p.ram_option = 1 GROUP BY (r.id)")
    List<Ram> findRamOfferProduct();
}
