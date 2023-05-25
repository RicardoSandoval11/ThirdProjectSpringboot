package com.ecommerce.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.IUserService;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;
    
    @GetMapping("/manage-users")
    public String manageUsers(Model model, Pageable pageable){

        Page<User> users = userService.allUsers(pageable);

        model.addAttribute("users", users);
        
        return "users/manage_users";
    }

    @PostMapping("/block-user")
    public String blockUser(@RequestParam("userId") Integer userId, RedirectAttributes attributes){

        User user = userService.getUserById(userId);
        user.setEnabled(0);
        userService.createUser(user);

        attributes.addFlashAttribute("msg", "User "+user.getName()+" has been disabled successfully");
        return "redirect:/users/manage-users";
    }

    @PostMapping("/enable-user")
    public String enableUser(@RequestParam("userId") Integer userId, RedirectAttributes attributes){

        User user = userService.getUserById(userId);
        user.setEnabled(1);
        userService.createUser(user);

        attributes.addFlashAttribute("msg", "User "+user.getName()+" has been enabled successfully");
        return "redirect:/users/manage-users";
    }
}
