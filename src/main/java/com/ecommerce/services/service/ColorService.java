package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Color;
import com.ecommerce.repository.ColorRepository;
import com.ecommerce.services.interfaces.IColorService;

@Service
public class ColorService implements IColorService{

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color getColorById(Integer colorId){
        Optional<Color> optional = colorRepository.findById(colorId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Color> getAllColors(){
        return colorRepository.findAll();
    }
    
}
