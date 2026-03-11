package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "payment_id")
    private Long paymentId;

    //@Column(name = "citizen_nic")
    private String citizenNic;

    //@Column(name = "amount")
    private BigDecimal amount;

    //@Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    //@Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    //@Column(name = "status")
    private PaymentStatus status;

    //@Column(name = "reference_no")
    private String referenceNo;

    //@Column(name = "description")
    private String description;
}
