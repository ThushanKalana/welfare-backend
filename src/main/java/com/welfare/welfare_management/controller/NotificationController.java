package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.dto.NotificationDTO;
import com.welfare.welfare_management.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotification(@RequestHeader("Authorization") String authHeader,@RequestBody NotificationDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(notificationService.sendNotification(authHeader,dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getNotifications/{citizenNic}")
    public ResponseEntity<?> getAllNotifications(@RequestHeader("Authorization") String authHeader,@PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(notificationService.getAllNotifications(authHeader,citizenNic));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/deleteNotification/{notificationId}")
    public ResponseEntity<?> deleteNotification(@RequestHeader("Authorization") String authHeader,@PathVariable Long notificationId) {
        try {
            return ResponseEntity.ok(
                    Map.of("message", notificationService.deleteNotification(authHeader,notificationId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/deleteAllNotifications/{citizenNic}")
    public ResponseEntity<?> deleteAllNotifications(@RequestHeader("Authorization") String authHeader,@PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(
                    Map.of("message", notificationService.deleteAllNotifications(authHeader,citizenNic)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
