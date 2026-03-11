package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
//@Table(name = "household_monthly_income")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMonthlyIncome {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "agriculture_income")
    private BigDecimal agricultureIncome;

    //@Column(name = "business_income")
    private BigDecimal businessIncome;

    //@Column(name = "self_employment_income")
    private BigDecimal selfEmploymentIncome;

    //@Column(name = "wage_income")
    private BigDecimal wageIncome;

    //@Column(name = "government_assistance_income")
    private BigDecimal governmentAssistanceIncome;

    //@Column(name = "pension_income")
    private BigDecimal pensionIncome;

    //@Column(name = "remittance_income")
    private BigDecimal remittanceIncome;

    //@Column(name = "rental_income")
    private BigDecimal rentalIncome;

    //@Column(name = "interest_income")
    private BigDecimal interestIncome;

    //@Column(name = "samurdhi_income")
    private BigDecimal samurdhiIncome;

    //@Column(name = "elder_income")
    private BigDecimal elderIncome;

    //@Column(name = "disabled_income")
    private BigDecimal disabledIncome;

    //@Column(name = "local_income")
    private BigDecimal localIncome;

    //@Column(name = "foreign_income")
    private BigDecimal foreignIncome;

    //@Column(name = "donation_income")
    private BigDecimal donationIncome;

    //@Column(name = "other_government_income")
    private BigDecimal otherGovernmentIncome;

    //@Column(name = "other_income")
    private BigDecimal otherIncome;

    //@Column(name = "total_income")
    @Column(insertable = false, updatable = false)
    private BigDecimal totalIncome;

    //@Column(name = "total_income", insertable = false, updatable = false)
    //private BigDecimal totalExpenses;
}
