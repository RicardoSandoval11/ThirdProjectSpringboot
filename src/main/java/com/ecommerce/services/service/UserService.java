package com.ecommerce.services.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.models.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.interfaces.IUserService;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email){
        Optional<User> optional = userRepository.findByEmail(email);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void createUser(User user){
        userRepository.save(user);
    }

    @Override
    public User getuserByToken(String token){
        Optional<User> optional = userRepository.findByToken(token);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public Page<User> allUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Integer userId){
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    
}
