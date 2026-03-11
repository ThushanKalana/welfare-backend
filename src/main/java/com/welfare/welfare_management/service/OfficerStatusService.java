package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.OfficerStatusDTO;
import com.welfare.welfare_management.model.OfficerStatus;
import com.welfare.welfare_management.model.ReviewStatus;
import com.welfare.welfare_management.repo.ApplicationRepo;
import com.welfare.welfare_management.repo.OfficerStatusRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class OfficerStatusService {

    @Autowired
    private OfficerStatusRepo officerStatusRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

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


    // CREATE - when citizen submits application

    public OfficerStatusDTO createOfficerStatus(String authHeader, Long applicationId) {

        validateToken(authHeader);

        applicationRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException(
                        "Application not found with ID: " + applicationId));

        // ✅ Check if already exists
        if (officerStatusRepo.existsById(applicationId)) {
            throw new RuntimeException(
                    "Officer status already exists for application: " + applicationId);
        }

        OfficerStatus officerStatus = new OfficerStatus();
        officerStatus.setApplicationId(applicationId);
        officerStatus.setGnOfficerStatus(ReviewStatus.PENDING);
        officerStatus.setSecondOfficerStatus(ReviewStatus.PENDING);
        officerStatus.setAdminStatus(ReviewStatus.PENDING);
        //officerStatus.setCreatedAt(LocalDateTime.now());

        return mapToDTO(officerStatusRepo.save(officerStatus));
    }


    // GET by application ID

    public OfficerStatusDTO getOfficerStatusById(String authHeader, Long applicationId) {

        validateToken(authHeader);

        OfficerStatus officerStatus = officerStatusRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException(
                        "Officer status not found for application: " + applicationId));

        return mapToDTO(officerStatus);
    }


    // UPDATE - GN Officer review

    public OfficerStatusDTO updateGnOfficerStatus(String authHeader, Long applicationId,
                                                  ReviewStatus status, String note) {

        validateToken(authHeader);

        OfficerStatus officerStatus = officerStatusRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException(
                        "Officer status not found for application: " + applicationId));

        officerStatus.setGnOfficerStatus(status);
        officerStatus.setGnOfficerNote(note);
        officerStatus.setGnReviewedAt(LocalDateTime.now());

        return mapToDTO(officerStatusRepo.save(officerStatus));
    }


    // UPDATE - Second Officer review

    public OfficerStatusDTO updateSecondOfficerStatus(String authHeader, Long applicationId,
                                                      ReviewStatus status, String note) {

        validateToken(authHeader);

        OfficerStatus officerStatus = officerStatusRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException(
                        "Officer status not found for application: " + applicationId));

        // ✅ Second officer can only review after GN officer approves
        if (officerStatus.getGnOfficerStatus() != ReviewStatus.APPROVED) {
            throw new RuntimeException(
                    "GN Officer must approve before Second Officer can review");
        }

        officerStatus.setSecondOfficerStatus(status);
        officerStatus.setSecondOfficerNote(note);
        officerStatus.setSecondReviewedAt(LocalDateTime.now());

        return mapToDTO(officerStatusRepo.save(officerStatus));
    }


    // UPDATE - Admin review

    public OfficerStatusDTO updateAdminStatus(String authHeader, Long applicationId,
                                              ReviewStatus status, String note) {

        validateToken(authHeader);

        OfficerStatus officerStatus = officerStatusRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException(
                        "Officer status not found for application: " + applicationId));

        // ✅ Admin can only review after Second Officer approves
        if (officerStatus.getSecondOfficerStatus() != ReviewStatus.APPROVED) {
            throw new RuntimeException(
                    "Second Officer must approve before Admin can review");
        }

        officerStatus.setAdminStatus(status);
        officerStatus.setAdminNote(note);
        officerStatus.setAdminReviewedAt(LocalDateTime.now());

        return mapToDTO(officerStatusRepo.save(officerStatus));
    }


    // HELPER

    private OfficerStatusDTO mapToDTO(OfficerStatus officerStatus) {
        OfficerStatusDTO dto = new OfficerStatusDTO();
        dto.setApplicationId(officerStatus.getApplicationId());
        dto.setGnOfficerStatus(officerStatus.getGnOfficerStatus());
        dto.setGnOfficerNote(officerStatus.getGnOfficerNote());
        dto.setGnReviewedAt(officerStatus.getGnReviewedAt());
        dto.setSecondOfficerStatus(officerStatus.getSecondOfficerStatus());
        dto.setSecondOfficerNote(officerStatus.getSecondOfficerNote());
        dto.setSecondReviewedAt(officerStatus.getSecondReviewedAt());
        dto.setAdminStatus(officerStatus.getAdminStatus());
        dto.setAdminNote(officerStatus.getAdminNote());
        dto.setAdminReviewedAt(officerStatus.getAdminReviewedAt());
        //dto.setCreatedAt(officerStatus.getCreatedAt());
        return dto;
    }
}
