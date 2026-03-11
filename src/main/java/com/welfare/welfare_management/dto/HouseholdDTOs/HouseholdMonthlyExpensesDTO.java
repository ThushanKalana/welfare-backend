package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HouseholdMonthlyExpensesDTO {
    private BigDecimal foodExpenses;
    private BigDecimal housingExpenses;
    private BigDecimal clothingExpenses;
    private BigDecimal electricityWaterFuel;
    private BigDecimal healthExpenses;
    private BigDecimal educationExpenses;
    private BigDecimal transportExpenses;
    private BigDecimal communicationExpenses;
    private BigDecimal loanInstallments;
    private BigDecimal insuranceSavings;
    private BigDecimal socialReligiousExpenses;
    private BigDecimal entertainmentExpenses;
    private BigDecimal otherExpenses;
    private BigDecimal totalExpenses;
}
