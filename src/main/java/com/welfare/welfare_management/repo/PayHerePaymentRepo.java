package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.PayHerePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayHerePaymentRepo extends JpaRepository<PayHerePayment, Long> {
    Optional<PayHerePayment> findByPaymentId(String paymentId);
}

