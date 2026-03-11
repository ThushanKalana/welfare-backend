package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdWaterSupply {

    @Id
    private Integer householdId;

    private Boolean protectedWell;
    private Boolean publicWell;
    private Boolean tubeWell;
    private Boolean pipeBorneWater;
    private Boolean rainwater;
    private Boolean bottledWater;
    private Boolean waterBowser;
    private Boolean otherWaterSupply;
}
