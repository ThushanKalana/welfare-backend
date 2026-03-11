package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_vehicle_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdVehicleAssets {

    @Id
    @Column(name = "household_id")
    private Integer householdId;

    @Column(name = "motor_bicycle_cc_125_or_less")
    private Boolean motorBicycleCc125OrLess;

    @Column(name = "motor_bicycle_above_cc_125")
    private Boolean motorBicycleAboveCc125;

    @Column(name = "three_wheeler")
    private Boolean threeWheeler;

    @Column(name = "motor_car")
    private Boolean motorCar;

    @Column(name = "van_or_jeep")
    private Boolean vanOrJeep;

    @Column(name = "bus")
    private Boolean bus;

    @Column(name = "tractor_or_trailer")
    private Boolean tractorOrTrailer;

    @Column(name = "heavy_vehicle_category_2")
    private Boolean heavyVehicleCategory2;

    @Column(name = "heavy_vehicle_category_4")
    private Boolean heavyVehicleCategory4;

    @Column(name = "other_transport_equipment")
    private Boolean otherTransportEquipment;
}
