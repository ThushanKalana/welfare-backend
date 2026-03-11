package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HouseholdMonthlyIncomeDTO {
    private BigDecimal agricultureIncome;
    private BigDecimal businessIncome;
    private BigDecimal selfEmploymentIncome;
    private BigDecimal wageIncome;
    private BigDecimal governmentAssistanceIncome;
    private BigDecimal pensionIncome;
    private BigDecimal remittanceIncome;
    private BigDecimal rentalIncome;
    private BigDecimal interestIncome;
    private BigDecimal samurdhiIncome;
    private BigDecimal elderIncome;
    private BigDecimal disabledIncome;
    private BigDecimal localIncome;
    private BigDecimal foreignIncome;
    private BigDecimal donationIncome;
    private BigDecimal otherGovernmentIncome;
    private BigDecimal otherIncome;
    private BigDecimal totalIncome;
}