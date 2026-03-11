package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_economic_equipment_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdEconomicEquipmentAssets {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "motor_boat")
    private Boolean motorBoat;

    //@Column(name = "non_motor_boat")
    private Boolean nonMotorBoat;

    //@Column(name = "combined_harvesting_machine")
    private Boolean combinedHarvestingMachine;

    //@Column(name = "other_agri_or_fishing_equipment")
    private Boolean otherAgriOrFishingEquipment;

    //@Column(name = "other_self_employment_tools")
    private Boolean otherSelfEmploymentTools;
}
