package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Storage;
import com.ecommerce.repository.StorageRepository;
import com.ecommerce.services.interfaces.IStorageService;

@Service
public class StorageService implements IStorageService{

    @Autowired
    private StorageRepository storageRepository;
    
    @Override
    public List<Storage> getStoragesWithOfferProducts(){
        return storageRepository.findStorageOfferproduct();
    }

    @Override
    public Storage getStorageById(Integer storageId){
        Optional<Storage> optional = storageRepository.findById(storageId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Storage> getllAllStorages(){
        return storageRepository.findAll();
    }
}
