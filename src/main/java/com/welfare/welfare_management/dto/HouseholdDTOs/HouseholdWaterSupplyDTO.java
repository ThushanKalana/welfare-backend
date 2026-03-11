package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

@Data
public class HouseholdWaterSupplyDTO {

    private Boolean protectedWell;
    private Boolean publicWell;
    private Boolean tubeWell;
    private Boolean pipeBorneWater;
    private Boolean rainwater;
    private Boolean bottledWater;
    private Boolean waterBowser;
    private Boolean otherWaterSupply;
}
