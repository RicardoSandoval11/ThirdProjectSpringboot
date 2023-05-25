package com.ecommerce.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.models.Advertisement;

public interface IAdvertisementService {
    
    List<Advertisement> getMostRecentAdvertisement();
    Advertisement getAdvertisementById(Integer id);
    Page<Advertisement> getAllAdvertisements(Pageable pageable);
    void advertisementCreateUpdate(Advertisement advertisement);
    void deleteAdvertisement(Advertisement advertisement);
}
