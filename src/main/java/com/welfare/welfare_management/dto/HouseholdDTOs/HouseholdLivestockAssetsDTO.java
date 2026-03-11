package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

@Data
public class HouseholdLivestockAssetsDTO {
    private Boolean cattle;
    private Boolean buffalo;
    private Boolean goats;
    private Boolean sheep;
    private Boolean poultry;
    private Boolean pigs;
    private Boolean otherLivestock;
}
