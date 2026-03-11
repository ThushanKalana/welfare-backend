package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

@Data
public class HouseholdPowerStatusDTO {
    private Boolean electricity;
    private Boolean solarPower;
    private Boolean generator;
    private Boolean biogas;
    private Boolean kerosene;
    private Boolean poor;
}
