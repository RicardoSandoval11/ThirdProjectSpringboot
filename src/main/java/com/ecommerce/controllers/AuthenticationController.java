package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.helpers.GenerateCode;
import com.ecommerce.models.Role;
import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.IRoleService;
import com.ecommerce.services.interfaces.IUserService;
import com.ecommerce.services.service.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // send emails
    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${ecommerceapp.baseurl.activateaccount}")
    private String baseActivateAccountUrl;

    @Value("${ecommerceapp.baseurl.recoverpassword}")
    private String baseRecoverPasswordUrl;
    
    @GetMapping("/login")
    public String LoginForm(Authentication authentication){

        if(authentication != null){
            return "redirect:/";
        }
        return "authentication/login";
    }

    @GetMapping("/register")
    public String RegisterForm(Authentication authentication){
        if(authentication != null){
            return "redirect:/";
        }
        return "authentication/register";
    }

    @PostMapping("/register")
    public String CreateAccount(@ModelAttribute User user, Model model, @RequestParam("password_confirmation") String passwordConfirmation, RedirectAttributes attributes){
        // verify email is not in use
        User userVerification = userService.getUserByEmail(user.getEmail());

        List<String> validationResults = new ArrayList<String>();

        if(userVerification != null){
            validationResults.add("The email address is already in use");
        }

        if(!user.getPassword().equals(passwordConfirmation)){
            validationResults.add("Passwords are different");
        }

        String regex = ".\\w.\\s.*";

        if(user.getName().matches(regex)){
            validationResults.add("Name cannot contain numbers or special characters");
        }

        if(!validationResults.isEmpty()){
            model.addAttribute("errors", validationResults);
            return "authentication/register";
        }

        // create account
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // default role 
        Role role = roleService.getRoleByName("Client");
        user.setRole(role);

        // activation account code
        String token = GenerateCode.generateCode();
        user.setToken(token);

        user.setEnabled(0);

        try {
            // send email
            emailService.sendConfirmationAccountEmail(emailSender, user.getEmail(), baseActivateAccountUrl+token, token);

            // create user
            userService.createUser(user);
            attributes.addFlashAttribute("msg_success", "We have sent you an email with the code with which you can activate your account and the link that you must access to enter it.");
            return "redirect:/auth/register";

        } catch (Exception e) {
            attributes.addFlashAttribute("send_mail_error", "Tried to create account but something went wrong. Please, try later.");
            return "redirect:/auth/register";
        }
    }

    @GetMapping("/activate-account/{token}")
    public String activateAccountForm(@PathVariable("token") String token, Authentication authentication){

        // verify user with the token exists
        User user = userService.getuserByToken(token);

        if(authentication != null){
            return "redirect:/";
        }

        if(user == null){
            return "authentication/error";
        }

        return "authentication/activate_account";

    }

    @PostMapping("/activate-account")
    public String activateAccount(@RequestParam("token") String token, RedirectAttributes attributes, Model model){

        User user = userService.getuserByToken(token);

        if(user == null){
            model.addAttribute("error", "Invalid code");
            return "authentication/activate_account";
        }else{

            // update user
            user.setEnabled(1);
            user.setToken(null);
            userService.createUser(user);

            attributes.addFlashAttribute("msg_success", "Your account has been activated successfully");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/recover-password")
    public String recoverPasswordForm(Authentication authentication){

        return "authentication/recover_password";
    }

    @PostMapping("/recover-password")
    public String recoverPassword(@RequestParam("email") String email, RedirectAttributes attributes){

        // verify if email exists
        User user = userService.getUserByEmail(email);

        if(user == null){
            attributes.addFlashAttribute("error_msg", "User with this email address doesn't exist");
            return "redirect:/auth/recover-password";
        }

        // send email
        try {

            // generate a token
            String token = GenerateCode.generateCode();

            user.setToken(token);

            userService.createUser(user);
            
            emailService.sendRecoverPasswordEmail(emailSender, email, baseRecoverPasswordUrl+token);

            attributes.addFlashAttribute("msg_success", "We have sent you an email with the link that you can access to update your password.");
            return "redirect:/auth/recover-password";

        } catch (Exception e) {
            attributes.addFlashAttribute("error_msg", "Tried to send email, but something went wrong. Try again later.");
            return "redirect:/auth/recover-password";
        }
    }

    @GetMapping("/update-password/{token}")
    public String updatePasswordForm(@PathVariable("token") String token, Model model){

        // verify if the token is valid
        User user = userService.getuserByToken(token);

        if(user == null){
            return "authentication/error";
        }

        model.addAttribute("userToken", token);
        return "authentication/update_password";
    }

    @PostMapping("/update-password")
    public String updatePassword(
                    @RequestParam("password") String password, 
                    @RequestParam("password_confirmation") String password_confirmation,
                    @RequestParam("userToken") String userToken,
                    Authentication authentication,
                    RedirectAttributes attributes){

        // validate passwords
        if(!password.equals(password_confirmation)){
            attributes.addFlashAttribute("error_msg", "Passwords are different");
            return "redirect:/auth/update-password/"+userToken;
        }

        // update password
        User user = userService.getuserByToken(userToken);
        user.setToken(null);
        String enc_password = passwordEncoder.encode(password);
        user.setPassword(enc_password);
        userService.createUser(user);

        if(authentication != null){
            attributes.addFlashAttribute("msg_success", "Password updated successfully");
            return "redirect:/account/my-account";
        }
        attributes.addFlashAttribute("msg_success", "Password updated successfully");
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication){
        if(authentication != null){
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);
            return "redirect:/login";
        }else{
            return "redirect:/";
        }
    }

}
