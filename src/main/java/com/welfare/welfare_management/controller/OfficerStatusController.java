package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.dto.OfficerStatusDTO;
import com.welfare.welfare_management.service.OfficerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class OfficerStatusController {

    @Autowired
    private OfficerStatusService officerStatusService;

    // Create officer status when citizen submits
    @PostMapping("/createOfficerStatus/{applicationId}")
    public ResponseEntity<?> createOfficerStatus(@RequestHeader("Authorization") String authHeader, @PathVariable Long applicationId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(officerStatusService.createOfficerStatus(authHeader,applicationId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // Get officer status by application ID
    @GetMapping("/getOfficerStatus/{applicationId}")
    public ResponseEntity<?> getOfficerStatus(@RequestHeader("Authorization") String authHeader, @PathVariable Long applicationId) {
        try {
            return ResponseEntity.ok(
                    officerStatusService.getOfficerStatusById(authHeader,applicationId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // GN Officer review
    @PutMapping("/gnOfficerReview/{applicationId}")
    public ResponseEntity<?> gnOfficerReview(@RequestHeader("Authorization") String authHeader,
            @PathVariable Long applicationId,
            @RequestBody OfficerStatusDTO dto) {
        try {
            return ResponseEntity.ok(officerStatusService.updateGnOfficerStatus(authHeader,
                    applicationId, dto.getGnOfficerStatus(), dto.getGnOfficerNote()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // Second Officer review
    @PutMapping("/secondOfficerReview/{applicationId}")
    public ResponseEntity<?> secondOfficerReview(@RequestHeader("Authorization") String authHeader,
            @PathVariable Long applicationId,
            @RequestBody OfficerStatusDTO dto) {
        try {
            return ResponseEntity.ok(officerStatusService.updateSecondOfficerStatus(authHeader,
                    applicationId, dto.getSecondOfficerStatus(), dto.getSecondOfficerNote()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // Admin review
    @PutMapping("/adminReview/{applicationId}")
    public ResponseEntity<?> adminReview(@RequestHeader("Authorization") String authHeader,
            @PathVariable Long applicationId,
            @RequestBody OfficerStatusDTO dto) {
        try {
            return ResponseEntity.ok(officerStatusService.updateAdminStatus(authHeader,
                    applicationId, dto.getAdminStatus(), dto.getAdminNote()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
