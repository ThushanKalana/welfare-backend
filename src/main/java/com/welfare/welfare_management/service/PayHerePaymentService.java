package com.welfare.welfare_management.service;

import com.welfare.welfare_management.model.PayHerePayment;
import com.welfare.welfare_management.repo.PayHerePaymentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
public class PayHerePaymentService {

    private final PayHerePaymentRepo repository;

    public PayHerePaymentService(PayHerePaymentRepo repository) {
        this.repository = repository;
    }

    @Transactional
    public void savePayment(Map<String, String> params) {
        PayHerePayment payment = new PayHerePayment();
        payment.setPaymentId(params.get("payment_id"));
        payment.setMerchantId(params.get("merchant_id"));
        payment.setStatusCode(params.get("status_code"));
        payment.setAmount(params.get("amount") != null ? Double.valueOf(params.get("amount")) : null);
        payment.setCurrency(params.get("currency"));
        payment.setPaymentMethod(params.get("payment_method"));
        payment.setCustomerName(params.get("customer_name"));
        payment.setCustomerEmail(params.get("customer_email"));
        payment.setHash(params.get("hash"));

        repository.save(payment);
    }
}