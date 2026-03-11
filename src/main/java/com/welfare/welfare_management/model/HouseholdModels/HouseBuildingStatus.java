package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// All child entities follow the same flat pattern
@Entity
//@Table(name = "house_building_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseBuildingStatus {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;   // shared PK, no @MapsId needed

    //@Column(name = "single_floor_house")
    private Boolean singleFloorHouse;

    //@Column(name = "two_floor_house")
    private Boolean twoFloorHouse;

    //@Column(name = "multi_floor_house")
    private Boolean multiFloorHouse;

    //@Column(name = "super_structure_house")
    private Boolean superStructureHouse;

    //@Column(name = "annex_house")
    private Boolean annexHouse;

    //@Column(name = "sub_house")
    private Boolean subHouse;

    //@Column(name = "double_house")
    private Boolean doubleHouse;

    //@Column(name = "line_house")
    private Boolean lineHouse;

    //@Column(name = "slum_house")
    private Boolean slumHouse;

    //@Column(name = "hut_house")
    private Boolean hutHouse;

    //@Column(name = "group_house")
    private Boolean groupHouse;

    //@Column(name = "other_house")
    private Boolean otherHouse;
}
