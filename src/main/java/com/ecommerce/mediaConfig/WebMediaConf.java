package com.ecommerce.mediaConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMediaConf implements WebMvcConfigurer{
    
    @Value("${ecommerceapp.path.advertisements}")
    private String advertisementImagesPath;

    @Value("${ecommerceapp.path.brands}")
    private String brandImagesPath;

    @Value("${ecommerceapp.path.categories}")
    private String categoriesImagePath;

    @Value("${ecommerceapp.path.products}")
    private String productsImagesPath;

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/advertisement-imgs/**").addResourceLocations("file:"+advertisementImagesPath);
        registry.addResourceHandler("/brand-imgs/**").addResourceLocations("file:"+brandImagesPath);
        registry.addResourceHandler("/categories-imgs/**").addResourceLocations("file:"+categoriesImagePath);
        registry.addResourceHandler("/products-imgs/**").addResourceLocations("file:"+productsImagesPath);
    }
}
