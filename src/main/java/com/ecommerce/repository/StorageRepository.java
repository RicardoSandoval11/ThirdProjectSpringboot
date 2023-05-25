package com.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.models.Storage;

public interface StorageRepository extends JpaRepository<Storage, Integer>{

    @Query("SELECT new com.ecommerce.models.Storage(s.id, s.value) FROM Storage s INNER JOIN ProductStorage ps ON ps.storageId = s.id INNER JOIN Product p ON p.id = ps.productId WHERE p.storage_option = 1 AND p.isOffer = 1 GROUP BY (s.id)")
    List<Storage> findStorageOfferproduct();
    
}
