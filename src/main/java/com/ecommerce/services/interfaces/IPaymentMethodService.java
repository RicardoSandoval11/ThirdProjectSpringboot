package com.ecommerce.services.interfaces;

import java.util.List;

import com.ecommerce.models.PaymentMethod;

public interface IPaymentMethodService {
    
    List<PaymentMethod> getAllPaymentMethods();
    PaymentMethod getMethodById(Integer paymentMethodId);
}
