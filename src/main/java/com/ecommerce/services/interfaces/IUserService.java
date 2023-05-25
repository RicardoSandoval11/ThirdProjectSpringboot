package com.ecommerce.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.models.User;

public interface IUserService {
    
    User getUserByEmail(String email);
    void createUser(User user);
    User getuserByToken(String token);
    User updateUser(User user);
    Page<User> allUsers(Pageable pageable);
    User getUserById(Integer userId);
}
