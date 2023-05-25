package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Advertisement;
import com.ecommerce.repository.AdvertisementRepository;
import com.ecommerce.services.interfaces.IAdvertisementService;

@Service
public class AdvertisementService implements IAdvertisementService{

    @Autowired
    private AdvertisementRepository advertisementRepository;
    
    @Override
    public List<Advertisement> getMostRecentAdvertisement(){

        return advertisementRepository.findMostRecentAdvertisements();

    }

    @Override
    public Advertisement getAdvertisementById(Integer id){
        Optional<Advertisement> optional = advertisementRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<Advertisement> getAllAdvertisements(Pageable pageable){
        return advertisementRepository.findAll(pageable);
    }

    @Override
    public void advertisementCreateUpdate(Advertisement advertisement){
        advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Advertisement advertisement){
        advertisementRepository.delete(advertisement);
    }
}
