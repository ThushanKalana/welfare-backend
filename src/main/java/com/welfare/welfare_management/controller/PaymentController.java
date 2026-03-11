package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.dto.PaymentDTO;
import com.welfare.welfare_management.service.PayHerePaymentService;
import com.welfare.welfare_management.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PayHerePaymentService payHerePaymentService;

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestHeader("Authorization") String authHeader,@RequestBody PaymentDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(paymentService.createPayment(authHeader,dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getPayments/{citizenNic}")
    public ResponseEntity<?> getPayments(@RequestHeader("Authorization") String authHeader,@PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentsByNic(authHeader,citizenNic));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getPaymentsByStatus/{citizenNic}/{status}")
    public ResponseEntity<?> getPaymentsByStatus(@RequestHeader("Authorization") String authHeader,
            @PathVariable String citizenNic,
            @PathVariable String status) {
        try {
            return ResponseEntity.ok(
                    paymentService.getPaymentsByStatus(authHeader,citizenNic, status));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/updatePaymentStatus/{paymentId}")
    public ResponseEntity<?> updatePaymentStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long paymentId,
            @RequestBody Map<String, String> request) {

        try {
            String status = request.get("status");

            return ResponseEntity.ok(
                    paymentService.updatePaymentStatus(authHeader, paymentId, status));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/deletePayment/{paymentId}")
    public ResponseEntity<?> deletePayment(@RequestHeader("Authorization") String authHeader,@PathVariable Long paymentId) {
        try {
            return ResponseEntity.ok(
                    Map.of("message", paymentService.deletePayment(authHeader,paymentId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Get all payments by status
    @GetMapping("/getAllPaymentsByStatus/{status}")
    public ResponseEntity<?> getAllPaymentsByStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String status) {
        try {
            return ResponseEntity.ok(
                    paymentService.getAllPaymentsByStatus(authHeader, status));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }


//    @PostMapping("/payhere/notify")
//    public ResponseEntity<String> payhereNotify(@RequestParam Map<String, String> params) {
//
//        // Save incoming payment
//        payHerePaymentService.savePayment(params);
//
//        // Update status if completed
//        String orderId = params.get("payment_id");
//        String statusCode = params.get("status_code");
//
////        if ("2".equals(statusCode)) {
////            paymentHereService.updatePayment(orderId, "COMPLETED");
////        }
//
//        return ResponseEntity.ok("OK");
//    }
}
