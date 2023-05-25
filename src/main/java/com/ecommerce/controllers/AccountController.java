package com.ecommerce.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.IUserService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IUserService userService;
    
    @GetMapping("/my-account")
    public String myAccount(Authentication authentication, Model model){
        if(authentication == null){
            return "redirect:/auth/login";
        }
        
        
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        user.setPassword(null);
        user.setEnabled(null);
        user.setRole(null);
        
        model.addAttribute("user", user);

        return "account/my_account";
    }

    @GetMapping("/update-information")
    public String updateAccountInformation(Authentication authentication, Model model){
        if(authentication == null){
            return "redirect:/auth/login";
        }

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        user.setPassword(null);
        user.setEnabled(null);
        user.setRole(null);

        model.addAttribute("user", user);

        return "account/update_information";
    }

    @PostMapping("/update-information")
    public String saveAccountChanges(Authentication authentication, @ModelAttribute User user, RedirectAttributes attributes){

        String email = authentication.getName();
        User currentUser = userService.getUserByEmail(email);

        if(user.getName() != null || !user.getName().equals("")){
            currentUser.setName(user.getName());
        }
        if(user.getUsername() != null || !user.getUsername().equals("")){
            currentUser.setUsername(user.getUsername());
        }

        // validate email
        if(!currentUser.getEmail().equals(user.getEmail())){

            String emailToValidate = user.getEmail();
            User validateUser = userService.getUserByEmail(emailToValidate);

            if(validateUser == null){
                currentUser.setEmail(emailToValidate);

            }else{
                attributes.addFlashAttribute("error", "User with this email already exists");
                return "redirect:/account/update-information";
            }
        } 

        // Crear una nueva instancia de Authentication con los datos actualizados

        User userDetails = userService.updateUser(currentUser);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userDetails.getRole().getName()));
        UsernamePasswordAuthenticationToken updatedAuthentication = new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword(), authorities);

        // Establecer la nueva instancia de Authentication en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

        attributes.addFlashAttribute("msg_success", "Information updated successfully");
        return "redirect:/account/my-account";
    }
}
