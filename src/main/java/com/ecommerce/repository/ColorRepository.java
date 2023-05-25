package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.models.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
    
}
