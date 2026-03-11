package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.PaymentDTO;
import com.welfare.welfare_management.model.Payment;
import com.welfare.welfare_management.model.PaymentStatus;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.repo.PaymentRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    private JwtUtil jwtUtil;



    // Validate JWT (reuse method)
    private void validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }
    }


    // CREATE payment

    public PaymentDTO createPayment(String authHeader,PaymentDTO dto) {

        validateToken(authHeader);

        citizenRepo.findById(dto.getCitizenNic())
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + dto.getCitizenNic()));

        Payment payment = new Payment();
        payment.setCitizenNic(dto.getCitizenNic());
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);  // ✅ always start as PENDING
        payment.setReferenceNo(dto.getReferenceNo());
        payment.setDescription(dto.getDescription());

        return mapToDTO(paymentRepo.save(payment));
    }


    // GET all payments for a citizen

    public List<PaymentDTO> getPaymentsByNic(String authHeader,String citizenNic) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        return paymentRepo
                .findByCitizenNicOrderByPaymentDateDesc(citizenNic)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    // GET payments by status

    public List<PaymentDTO> getPaymentsByStatus(String authHeader,String citizenNic, String status) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        PaymentStatus paymentStatus;
        try {
            paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status +
                    ". Allowed: PENDING, COMPLETED, FAILED, CANCELLED");
        }

        return paymentRepo
                .findByCitizenNicAndStatusOrderByPaymentDateDesc(citizenNic, paymentStatus)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<PaymentDTO> getAllPaymentsByStatus(String authHeader, String status) {

        validateToken(authHeader);

        PaymentStatus paymentStatus;
        try {
            paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status +
                    ". Allowed: PENDING, COMPLETED, FAILED, CANCELLED");
        }

        // ✅ Fetch and map payments by status
        return paymentRepo.findByStatus(paymentStatus)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    // UPDATE payment status

    public PaymentDTO updatePaymentStatus(String authHeader,Long paymentId, String status) {

        validateToken(authHeader);

        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException(
                        "Payment not found with ID: " + paymentId));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new RuntimeException("Completed payment cannot be modified");
        }

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Status cannot be empty");
        }

        PaymentStatus paymentStatus;
        try {
            paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status +
                    ". Allowed: PENDING, COMPLETED, FAILED, CANCELLED");
        }

        payment.setStatus(paymentStatus);
        return mapToDTO(paymentRepo.save(payment));
    }


    // DELETE payment

    public String deletePayment(String authHeader,Long paymentId) {

        validateToken(authHeader);

        paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException(
                        "Payment not found with ID: " + paymentId));

        paymentRepo.deleteById(paymentId);
        return "Payment deleted successfully";
    }


    // HELPER

    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setCitizenNic(payment.getCitizenNic());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setReferenceNo(payment.getReferenceNo());
        dto.setDescription(payment.getDescription());
        return dto;
    }

}
