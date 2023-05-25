package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.models.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer>{

    @Query("SELECT new com.ecommerce.models.Advertisement(a.id, a.title, a.subtitle, a.content, a.image, a.createdAt) FROM Advertisement a ORDER BY a.createdAt DESC LIMIT 5")
    List<Advertisement> findMostRecentAdvertisements();
    
}
