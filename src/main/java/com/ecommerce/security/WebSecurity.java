package com.ecommerce.security;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager user = new JdbcUserDetailsManager(dataSource);
        user.setUsersByUsernameQuery("select email, password, enabled from users where email=?");
        user.setAuthoritiesByUsernameQuery("select users.email, roles.name from users inner join roles on roles.id=users.role where users.email = ?");
        return user;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        
              
        httpSecurity.authorizeHttpRequests()
                    .requestMatchers("/css/**", "/js/**").permitAll() // static resources
                    .requestMatchers("/advertisement-imgs/**", "/brand-imgs/**", "/categories-imgs/**", "/products-imgs/**").permitAll() // images
                    .requestMatchers(
                                    "/auth/**", 
                                    "/", 
                                    "/products/details/**",
                                    "/offers/**",
                                    "/categories/all-categories",
                                    "/brands/all-brands",
                                    "/products/all-products",
                                    "/advertisements/details/**").permitAll() // public routes
                    .requestMatchers(
                                "/products/manage-products",
                                            "/products/out-of-stock",
                                            "/products/update-product/**",
                                            "/products/save-changes",
                                            "/products/new-product",
                                            "/products/save-product",
                                            "/products/delete-product",
                                            "/admin/dashboard",
                                            "/categories/manage-categories",
                                            "/categories/update-category/**",
                                            "/categories/create-category",
                                            "/categories/save-category",
                                            "/brands/update-brand/**",
                                            "/brands/manage-brands",
                                            "/brands/save-brand",
                                            "/brands/create-brand",
                                            "/advertisements/manage-advertisements",
                                            "/users/manage-users",
                                            "/users/block-user",
                                            "/users/enable-user",
                                            "/order/manage-orders",
                                            "/order/pending-orders",
                                            "/order/details/**",
                                            "/order/update-status",
                                            "/advertisements/update-advertisement/**",
                                            "/advertisements/save-advertisement",
                                            "/advertisements/create-advertisement",
                                            "/advertisements/delete-advertisement").hasAnyAuthority("Administrator")
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/auth/login").defaultSuccessUrl("/",true).permitAll()
                    .and().logout().permitAll();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
