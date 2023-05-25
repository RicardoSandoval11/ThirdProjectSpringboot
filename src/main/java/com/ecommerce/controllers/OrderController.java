package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.models.Cart;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.models.OrderStatus;
import com.ecommerce.models.PaymentMethod;
import com.ecommerce.models.Product;
import com.ecommerce.models.User;
import com.ecommerce.services.interfaces.ICartItemService;
import com.ecommerce.services.interfaces.ICartService;
import com.ecommerce.services.interfaces.IOrderItemService;
import com.ecommerce.services.interfaces.IOrderService;
import com.ecommerce.services.interfaces.IOrderStatusService;
import com.ecommerce.services.interfaces.IPaymentMethodService;
import com.ecommerce.services.interfaces.IProductService;
import com.ecommerce.services.interfaces.IUserService;

@Controller
@RequestMapping("/order") 
public class OrderController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderStatusService orderStatusService;

    @PostMapping("/set-order")
    public String setNewOrder(){
        return "redirect:/order/new-order";
    }
    
    @GetMapping("/new-order")
    public String setnewOrder(Authentication authentication, Model model){

        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);
        Cart userCart = cartService.getCartByUser(user);

        List<CartItem> cartItems = cartItemService.getCartItemsByCart(userCart);

        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();

        model.addAttribute("cart_items", cartItems);
        model.addAttribute("cart", userCart);
        model.addAttribute("payment_methods", paymentMethods);
        return "orders/new_order";
    }

    @PostMapping("/save-order")
    public String saveNewOrder(
        @RequestParam(name = "email_address", required = true) String emailAddress,
        @RequestParam(name = "bill_to", required = true) String billTo,
        @RequestParam(name = "arn_bill", required = true) Double arn,
        @RequestParam(name = "payment_method", required = true) Integer paymentMethodId,
        @RequestParam(name = "fiscal_address", required = false) String fiscalAddress,
        @RequestParam(name = "first_name", required = true) String firstName,
        @RequestParam(name = "last_name", required = true) String lastName,
        @RequestParam(name = "phone_number", required = true) String phoneNumber,
        @RequestParam(name = "arn_delivery", required = false) Double arnDelivery,
        @RequestParam(name = "message", required = false) String extraMessage,
        @RequestParam(name = "delivery_address", required = false) String deliveryAddress,
        @RequestParam(name = "country", required = false) String country,
        @RequestParam(name = "state", required = false) String state,
        RedirectAttributes attributes,
        Model model,
        Authentication authentication

    ){
        Order newOrder = new Order();

        // set values of the new order
        newOrder.setEmailAddress(emailAddress);
        newOrder.setBillTo(billTo);
        newOrder.setArn(arn);
        if (fiscalAddress != null) {
            newOrder.setFiscalAddress(fiscalAddress);
        }
        newOrder.setFirstName(firstName);
        newOrder.setLastName(lastName);
        newOrder.setPhoneNumber(phoneNumber);
        if(arnDelivery != null){
            newOrder.setArnDelivery(arn);
        }else{
            newOrder.setArnDelivery(arn);
        }
        // set payment method
        PaymentMethod paymentMethod = paymentMethodService.getMethodById(paymentMethodId);
        newOrder.setPaymentMethod(paymentMethod);

        if(extraMessage != null){
            newOrder.setMessage(extraMessage);
        }

        // find user cart
        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);
        Cart userCart = cartService.getCartByUser(user);

        // set initial order status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        orderStatus.setValue("Pending");
        newOrder.setOrderStatus(orderStatus);

        // fields for delivery choice
        if(deliveryAddress != "" && country != "" && state != ""){
            newOrder.setDeliveryAddress(deliveryAddress);
            newOrder.setCountry(country);
            newOrder.setState(state);
            newOrder.setTotal(20.00);
        }else{
            newOrder.setDeliveryAddress(null);
            newOrder.setCountry(null);
            newOrder.setState(null);
            newOrder.setTotal(0.00);
        }
        newOrder.setUser(user);
        newOrder.setCreatedAt(new Date());

        // save order and order items
        Order createdOrder = orderService.createOrder(newOrder);

        
        List<CartItem> cartItems = cartItemService.getCartItemsByCart(userCart);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        
        for (CartItem cartItem : cartItems) {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setColor(cartItem.getColor());
            newOrderItem.setOrder(createdOrder);
            newOrderItem.setProduct(cartItem.getProduct());

            newOrderItem.setQuantity(cartItem.getQuantity());

            if(cartItem.getQuantity() > cartItem.getProduct().getStock()){
                Double subTotal;
                Double revenue;
                if(cartItem.getProduct().getIsOffer() == 1){
                    subTotal = cartItem.getProduct().getStock()*cartItem.getProduct().getOfferPrice();
                    revenue = cartItem.getProduct().getStock()*cartItem.getProduct().getOfferPrice() - cartItem.getQuantity()*cartItem.getProduct().getPurchase_price();
                }else{
                    subTotal = cartItem.getProduct().getStock()*cartItem.getProduct().getSellPrice();
                    revenue = cartItem.getProduct().getStock()*cartItem.getProduct().getSellPrice() - cartItem.getQuantity()*cartItem.getProduct().getPurchase_price();
                }
                newOrderItem.setSubTotal(subTotal);
                newOrderItem.setRevenue(revenue);

                // reduce stock
                Product product = productService.getProductById(cartItem.getProduct().getId());
                product.setStock(0);
                productService.updateProduct(product);
            }else{
                newOrderItem.setSubTotal(cartItem.getSubTotal());
                Double revenue;
                if(cartItem.getProduct().getIsOffer() == 1){
                    revenue = cartItem.getSubTotal() - cartItem.getQuantity()*cartItem.getProduct().getPurchase_price();
                }else{
                    revenue = cartItem.getSubTotal() - cartItem.getQuantity()*cartItem.getProduct().getPurchase_price();
                }
                newOrderItem.setRevenue(revenue);

                // reduce stock
                Product product = productService.getProductById(cartItem.getProduct().getId());
                product.setStock(product.getStock() - cartItem.getQuantity());
                productService.updateProduct(product);
            }

            if(cartItem.getProduct().getRam_option() == 1 && cartItem.getProduct().getStorage_option() == 1){
                newOrderItem.setStorage(cartItem.getStorage());
                newOrderItem.setRam(cartItem.getRam());   
            }

            // increase total order amount
            createdOrder.setTotal(createdOrder.getTotal() + newOrderItem.getSubTotal());
            
            
            orderItems.add(newOrderItem);
        }

        // save order
        orderService.updateOrder(createdOrder);

        // create all order items, remove all cart items and set the totla of the cart to zero
        userCart.setTotal(0.00);
        orderItemService.saveAllOrderItems(orderItems);
        cartItemService.deleteAllCartItems(cartItems);
        cartService.UpdateCart(userCart);

        // redirect user
        attributes.addFlashAttribute("msg", "Your order was received");
        return "redirect:/order/order-details/"+createdOrder.getId();
    }

    @GetMapping("/order-details/{orderId}")
    public String orderDetails(@PathVariable("orderId") Integer orderId, Model model, Authentication authentication){

        if(authentication == null){
            return "redirect:/";
        }

        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        Order order = orderService.getOrderById(orderId);

        if(order.getUser().getId() != user.getId()){
            return "redirect:/";
        }

        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("order_items", orderItems);
        
        return "orders/order_details";
    }

    @GetMapping("/track-order")
    public String trackOrder(@RequestParam(name="orderId", required=false) Integer orderId, Authentication authentication, Model model){
        if(authentication == null){
            return "redirect:/auth/login";
        }

        if(orderId == null){
            model.addAttribute("result", false);
            return "orders/track_order";
        }

        // find order
        Order order = orderService.getOrderById(orderId);

        if(order == null){
            model.addAttribute("result", true);
            return "orders/track_order";
        }

        // verify permissions
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        if(order.getUser().getId() != user.getId()){
            model.addAttribute("result", true);
            return "orders/track_order";
        }
        
        model.addAttribute("order_status", order.getOrderStatus());
        model.addAttribute("result", true);
        return "orders/track_order";
    }

    @GetMapping("/my-orders")
    public String userOrders(Authentication authentication, Model model, Pageable pageable){
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Page<Order> orders = orderService.getOrdersByUser(user, pageable);

        model.addAttribute("orders", orders);
        return "orders/user_orders";
    }

    @GetMapping("/manage-orders")
    public String manageOrders(Model model, Pageable pageable){
        Page<Order> orders = orderService.getAllOrders(pageable);
        model.addAttribute("orders", orders);
        return "orders/manage_orders";
    }

    @GetMapping("/pending-orders")
    public String pendingOrders(@RequestParam(required = false, name = "statusId") Integer orderStatudId, Model model, Pageable pageable){

        List<OrderStatus> statuses = orderStatusService.getUncompletedStatuses();
        model.addAttribute("statuses", statuses);

        if(orderStatudId != null){
            // find the status
            OrderStatus orderStatus = orderStatusService.getStatusById(orderStatudId);
            Page<Order> orders = orderService.getPendingOrdersByStatus(orderStatus, pageable);
            model.addAttribute("orders", orders);
        }
        else{
            // find completed order status
            OrderStatus orderStatus = orderStatusService.getStatusById(4);
            Page<Order> orders = orderService.getPendingOrders(orderStatus, pageable);
            model.addAttribute("orders", orders);
        }
        return "orders/pending_orders";
    }

    @GetMapping("/details/{orderId}")
    public String orderDetails(@PathVariable("orderId") Integer orderId, Model model){
        
        Order order = orderService.getOrderById(orderId);

        List<OrderStatus> statuses = orderStatusService.getAllStatuses();
        model.addAttribute("statuses", statuses);

        if(order == null){
            return "generic/not_found";
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("order_items", orderItems);
        return "orders/details_admin";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId, @RequestParam("statusId") Integer statusId, RedirectAttributes attributes){

        Order order = orderService.getOrderById(orderId);

        if(order == null){
            return "redirect:/order/manage-orders";
        }

        OrderStatus orderStatus = orderStatusService.getStatusById(statusId);

        order.setOrderStatus(orderStatus);

        orderService.updateOrder(order);

        attributes.addFlashAttribute("msg", "The order number #"+orderId+" has ben updated to: "+orderStatus.getValue());
        return "redirect:/order/details/"+orderId;
    }

}
