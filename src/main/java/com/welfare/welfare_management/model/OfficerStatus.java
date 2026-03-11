package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "officer_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerStatus {

    @Id
    //@Column(name = "application_id")
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    //@Column(name = "gn_officer_status")
    private ReviewStatus gnOfficerStatus = ReviewStatus.PENDING;

    //@Column(name = "gn_officer_note")
    private String gnOfficerNote;

    //@Column(name = "gn_reviewed_at")
    private LocalDateTime gnReviewedAt;

    @Enumerated(EnumType.STRING)
    //@Column(name = "second_officer_status")
    private ReviewStatus secondOfficerStatus = ReviewStatus.PENDING;

    //@Column(name = "second_officer_note")
    private String secondOfficerNote;

    //@Column(name = "second_reviewed_at")
    private LocalDateTime secondReviewedAt;

    @Enumerated(EnumType.STRING)
    //@Column(name = "admin_status")
    private ReviewStatus adminStatus = ReviewStatus.PENDING;

    //@Column(name = "admin_note")
    private String adminNote;

    //@Column(name = "admin_reviewed_at")
    private LocalDateTime adminReviewedAt;

//    @Column(name = "created_at")
//    private LocalDateTime createdAt;

//    // ✅ Relation with application
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "application_id", referencedColumnName = "application_id",
//            insertable = false, updatable = false)
//    @JsonIgnore
//    @ToString.Exclude
//    private Application application;
}
