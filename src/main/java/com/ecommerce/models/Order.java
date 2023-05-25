package com.ecommerce.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_order", schema = "ecommercedb")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "bill_to")
    private String billTo;
    private Double arn;
    @Column(name = "fiscal_address")
    private String fiscalAddress;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "delivery_address")
    private String deliveryAddress;
    private String country;
    private String state;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "arn_delivery")
    private Double arnDelivery;
    private String message;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    private Double total;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentId")
    private PaymentMethod paymentMethod;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusId")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getBillTo() {
        return billTo;
    }
    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }
    public Double getArn() {
        return arn;
    }
    public void setArn(Double arn) {
        this.arn = arn;
    }
    public String getFiscalAddress() {
        return fiscalAddress;
    }
    public void setFiscalAddress(String fiscalAddress) {
        this.fiscalAddress = fiscalAddress;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Double getArnDelivery() {
        return arnDelivery;
    }
    public void setArnDelivery(Double arnDelivery) {
        this.arnDelivery = arnDelivery;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", emailAddress=" + emailAddress + ", billTo=" + billTo + ", arn=" + arn
                + ", fiscalAddress=" + fiscalAddress + ", firstName=" + firstName + ", lastName=" + lastName
                + ", deliveryAddress=" + deliveryAddress + ", country=" + country + ", state=" + state
                + ", phoneNumber=" + phoneNumber + ", arnDelivery=" + arnDelivery + ", message=" + message + ", user="
                + user + ", total=" + total + ", createdAt=" + createdAt + ", paymentMethod=" + paymentMethod
                + ", orderStatus=" + orderStatus + "]";
    }

}
