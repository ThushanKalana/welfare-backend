package com.welfare.welfare_management.dto.HouseholdDTOs;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class HouseholdBuildingAssetsDTO {
    private Boolean hasAssetLand;
    private Boolean assetType1HalfAcre;
    private Boolean assetType1OneAcre;
    private Boolean hasAssetMudLand;
    private Boolean assetType2OneAcre;
    private Boolean assetType2MoreThanOne;
}

