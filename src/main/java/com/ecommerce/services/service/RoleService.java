package com.ecommerce.services.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.Role;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.services.interfaces.IRoleService;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name){
        Optional<Role> role = roleRepository.findByName(name);
        if(role.isPresent()){
            return role.get();
        }
        return null;
    }
    
}
