package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.Color;

public interface IColorService {
    
    Color getColorById(Integer colorId);
    List<Color> getAllColors();
}
