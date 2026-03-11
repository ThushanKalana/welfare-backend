package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMemberDTO {
    private Integer memberId;
    private String fullName;
    private Integer relationshipToHead;
    private Integer gender;
    private Integer marriageStatus;
    private LocalDate dateOfBirth;
    private Integer age;
    private String nationalId;
    private Integer educationLevel;
    private Integer studentStatus;
    private Integer employmentStatus;
    private BigDecimal monthlyIncome;
    private Integer socialWelfareBenefit;
    private Integer difficultyStatus;
    private Integer disabilityStatus;
    private Integer chronicIllness;
}