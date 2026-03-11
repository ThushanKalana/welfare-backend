package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.Payment;
import com.welfare.welfare_management.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

    List<Payment> findByCitizenNicOrderByPaymentDateDesc(String citizenNic);

    List<Payment> findByCitizenNicAndStatusOrderByPaymentDateDesc(
            String citizenNic, PaymentStatus status);

    List<Payment> findByStatus(PaymentStatus status);

    @Modifying
    @Query("DELETE FROM Payment p WHERE p.citizenNic = :citizenNic")
    void deleteByCitizenNic(@Param("citizenNic") String citizenNic);
}
