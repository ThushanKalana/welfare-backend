package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


// HouseholdMember - the only one with its own auto-increment PK
@Entity
@Table(name = "household_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "household_id")
    private Integer householdId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "relationship_to_head")
    private Integer relationshipToHead;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "marriage_status")
    private Integer marriageStatus;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "education_level")
    private Integer educationLevel;

    @Column(name = "student_status")
    private Integer studentStatus;

    @Column(name = "employment_status")
    private Integer employmentStatus;

    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;

    @Column(name = "social_welfare_benefit")
    private Integer socialWelfareBenefit;

    @Column(name = "difficulty_status")
    private Integer difficultyStatus;

    @Column(name = "disability_status")
    private Integer disabilityStatus;

    @Column(name = "chronic_illness")
    private Integer chronicIllness;
}

