package com.welfare.welfare_management.controller;


import com.welfare.welfare_management.dto.ApplicationDTO;
import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.Status;
import com.welfare.welfare_management.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;


    // Get all applications
    @GetMapping("/getApplications")
    public ResponseEntity<?> getApplications(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(applicationService.getAllApplications(authHeader));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Create application
    @PostMapping("/addApplication")
    public ResponseEntity<?> createApplication(@RequestHeader("Authorization") String authHeader,@RequestBody ApplicationDTO dto) {
        try {
            return ResponseEntity.ok(applicationService.createApplication(authHeader,dto));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Update application
    @PutMapping("/updateApplication")
    public ResponseEntity<?> updateApplication(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ApplicationDTO dto) {

        try {
            return ResponseEntity.ok(applicationService.updateApplication(authHeader, dto));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Delete application
    @DeleteMapping("/deleteApplication/{id}")
    public ResponseEntity<?> deleteApplication(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {

        try {
            String message = applicationService.deleteApplication(authHeader, id);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Get application by ID
    @GetMapping("/getApplicationById/{id}")
    public ResponseEntity<?> getApplicationById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {

        try {
            return ResponseEntity.ok(applicationService.getApplicationById(authHeader, id));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Get applications by citizen NIC
    @GetMapping("/getApplicationsByCitizen/{nic}")
    public ResponseEntity<?> getByCitizen(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String nic) {

        try {
            return ResponseEntity.ok(applicationService.getApplicationByCitizen(authHeader, nic));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Officer approve / reject application using @RequestBody
    @PutMapping("/updateApplicationStatus/{id}")
    public ResponseEntity<?> updateStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id,
            @RequestBody ApplicationDTO applicationDTO) {

        try {
            return ResponseEntity.ok(
                    applicationService.updateStatus(authHeader, id, applicationDTO.getStatus()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


}
