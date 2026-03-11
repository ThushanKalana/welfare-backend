package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_livestock__assets")   // note double underscore from your DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdLivestockAssets {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "cattle")
    private Boolean cattle;

    //@Column(name = "buffalo")
    private Boolean buffalo;

    //@Column(name = "goats")
    private Boolean goats;

    //@Column(name = "sheep")
    private Boolean sheep;

    //@Column(name = "poultry")
    private Boolean poultry;

    //@Column(name = "pigs")
    private Boolean pigs;

    //@Column(name = "other_livestock")
    private Boolean otherLivestock;
}
