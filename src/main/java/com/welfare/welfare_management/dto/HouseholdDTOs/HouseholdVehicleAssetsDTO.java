package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.Data;

@Data
public class HouseholdVehicleAssetsDTO {
    private Boolean motorBicycleCc125OrLess;
    private Boolean motorBicycleAboveCc125;
    private Boolean threeWheeler;
    private Boolean motorCar;
    private Boolean vanOrJeep;
    private Boolean bus;
    private Boolean tractorOrTrailer;
    private Boolean heavyVehicleCategory2;
    private Boolean heavyVehicleCategory4;
    private Boolean otherTransportEquipment;
}
