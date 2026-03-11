package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_building_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdBuildingAssets {

    @Id
    @Column(name = "household_id")
    private Integer householdId;

    @Column(name = "has_asset_type_1")
    private Boolean hasAssetLand;

    @Column(name = "asset_type_1_half_unit")
    private Boolean assetType1HalfAcre;

    @Column(name = "asset_type_1_one_unit")
    private Boolean assetType1OneAcre;

    @Column(name = "has_asset_type_2")
    private Boolean hasAssetMudLand;

    @Column(name = "asset_type_2_one_unit")
    private Boolean assetType2OneAcre;

    @Column(name = "asset_type_2_more_than_one")
    private Boolean assetType2MoreThanOne;
}