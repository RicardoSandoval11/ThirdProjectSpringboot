package com.ecommerce.services.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.models.PaymentMethod;
import com.ecommerce.repository.PaymentMethodRepository;
import com.ecommerce.services.interfaces.IPaymentMethodService;

@Service
public class PaymentMethodService implements IPaymentMethodService{

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    
    @Override
    public List<PaymentMethod> getAllPaymentMethods(){
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getMethodById(Integer paymentMethodId){
        Optional<PaymentMethod> optional = paymentMethodRepository.findById(paymentMethodId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
