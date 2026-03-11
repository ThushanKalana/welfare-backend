package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_power_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdPowerStatus {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "electricity")
    private Boolean electricity;

    //@Column(name = "solar_power")
    private Boolean solarPower;

    //@Column(name = "generator")
    private Boolean generator;

    //@Column(name = "biogas")
    private Boolean biogas;

    //@Column(name = "kerosene")
    private Boolean kerosene;

    //@Column(name = "poor")
    private Boolean poor;
}
