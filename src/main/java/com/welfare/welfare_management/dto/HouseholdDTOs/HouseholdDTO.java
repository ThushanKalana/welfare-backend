package com.welfare.welfare_management.dto.HouseholdDTOs;

import com.welfare.welfare_management.model.HouseholdModels.HouseholdStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdDTO {

    // ── Household core ──────────────────────────────────────────
    private Integer householdId;
    private String address;
    private String district;
    private String gnDivision;
    private Long applicationId;
    private String headNic;
    private HouseholdStatus status;

    // ── Child DTOs ───────────────────────────────────────────────
    private HouseBuildingStatusDTO buildingStatus;
    private HouseOwnershipStatusDTO ownershipStatus;
    private HouseholdBuildingAssetsDTO buildingAssets;
    private HouseholdEconomicEquipmentAssetsDTO economicEquipmentAssets;
    private HouseholdHouseEquipmentAssetsDTO houseEquipmentAssets;
    private HouseholdLivestockAssetsDTO livestockAssets;
    private HouseholdMonthlyExpensesDTO monthlyExpenses;
    private HouseholdMonthlyIncomeDTO monthlyIncome;
    private HouseholdPowerStatusDTO powerStatus;
    private HouseholdWaterSupplyDTO waterSupply;
    private HouseholdVehicleAssetsDTO vehicleAssets;
    private List<HouseholdMemberDTO> members;
    }

