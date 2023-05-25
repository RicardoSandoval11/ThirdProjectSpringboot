package com.ecommerce.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.customObjects.orderDateObject;
import com.ecommerce.models.Order;
import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.IOrderItemService;
import com.ecommerce.services.interfaces.IOrderService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IUserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;
    
    @GetMapping("/dashboard")
    public String Dashboard(Authentication authentication, Model model){

        if(authentication == null){
            return "redirect:/";
        }

        // statistics value
        Integer pendingOrders = orderService.getNumberOfPendingOrders();

        // revenue of the month
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(year, month, 1);
        Date startDate = calendar.getTime();

        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date endDate = calendar.getTime();

        Double monthRevenue = orderItemService.monthRevenue(startDate, endDate);

        // orders of the month
        Integer monthOrders = orderService.getNumberOfOrders(startDate, endDate);

        // user information
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        // number of products without stock
        Integer outOfStockProductsNumber = productService.getOutOfStockProductsNumber();

        // data for charts
        List<orderDateObject> orders = orderService.getOrdersOfMonth(startDate, endDate);

        // recent orders
        List<Order> recentOrders = orderService.getRecentOrders();

        model.addAttribute("username", user.getName());
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("monthRevenue", monthRevenue);
        model.addAttribute("monthOrders", monthOrders);
        model.addAttribute("outOfStockProductsNumber", outOfStockProductsNumber);
        model.addAttribute("orders", orders);
        model.addAttribute("recentOrders", recentOrders);

        return "administration/admin_panel";
    }
}
