package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "household_house_equipment_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdHouseEquipmentAssets {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "gas_cooker")
    private Boolean gasCooker;

    //@Column(name = "electric_cooker")
    private Boolean electricCooker;

    //@Column(name = "electric_fan")
    private Boolean electricFan;

    //@Column(name = "radio_or_music_player")
    private Boolean radioOrMusicPlayer;

    //@Column(name = "television")
    private Boolean television;

    //@Column(name = "refrigerator")
    private Boolean refrigerator;

    //@Column(name = "computer_or_laptop")
    private Boolean computerOrLaptop;
}
