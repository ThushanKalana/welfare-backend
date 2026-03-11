package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.model.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerStatusDTO {
    private Long applicationId;
    private ReviewStatus gnOfficerStatus;
    private String gnOfficerNote;
    private LocalDateTime gnReviewedAt;
    private ReviewStatus secondOfficerStatus;
    private String secondOfficerNote;
    private LocalDateTime secondReviewedAt;
    private ReviewStatus adminStatus;
    private String adminNote;
    private LocalDateTime adminReviewedAt;
}
