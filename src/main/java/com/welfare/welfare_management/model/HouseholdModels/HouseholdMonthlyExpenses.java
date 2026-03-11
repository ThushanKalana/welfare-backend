package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
//@Table(name = "household_monthly_expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMonthlyExpenses {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "food_expenses")
    private BigDecimal foodExpenses;

    //@Column(name = "housing_expenses")
    private BigDecimal housingExpenses;

    //@Column(name = "clothing_expenses")
    private BigDecimal clothingExpenses;

    //@Column(name = "electricity_water_fuel")
    private BigDecimal electricityWaterFuel;

    //@Column(name = "health_expenses")
    private BigDecimal healthExpenses;

    //@Column(name = "education_expenses")
    private BigDecimal educationExpenses;

    //@Column(name = "transport_expenses")
    private BigDecimal transportExpenses;

    //@Column(name = "communication_expenses")
    private BigDecimal communicationExpenses;

    //@Column(name = "loan_installments")
    private BigDecimal loanInstallments;

    //@Column(name = "insurance_savings")
    private BigDecimal insuranceSavings;

    //@Column(name = "social_religious_expenses")
    private BigDecimal socialReligiousExpenses;

    //@Column(name = "entertainment_expenses")
    private BigDecimal entertainmentExpenses;

    //@Column(name = "other_expenses")
    private BigDecimal otherExpenses;

    //@Column(name = "total_expenses")
    //private BigDecimal totalExpenses;
    @Column(name = "total_expenses", insertable = false, updatable = false)
    private BigDecimal totalExpenses;
}
