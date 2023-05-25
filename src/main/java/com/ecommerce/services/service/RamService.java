package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Ram;
import com.ecommerce.repository.RamRepository;
import com.ecommerce.services.interfaces.IRamService;

@Service
public class RamService implements IRamService{

    @Autowired
    private RamRepository ramRepository;
    
    @Override
    public List<Ram> getRamOfferProducts(){
        return ramRepository.findRamOfferProduct();
    }

    @Override
    public Ram getRamById(Integer ramId){
        Optional<Ram> optional = ramRepository.findById(ramId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Ram> getAllRam(){
        return ramRepository.findAll();
    }
}
