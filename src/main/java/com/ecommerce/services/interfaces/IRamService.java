package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.Ram;

public interface IRamService {
    
    List<Ram> getRamOfferProducts();
    Ram getRamById(Integer ramId);
    List<Ram> getAllRam();
}
