package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "house_ownership_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseOwnershipStatus {

    @Id
    //@Column(name = "household_id")
    private Integer householdId;

    //@Column(name = "has_own_house")
    private Boolean hasOwnHouse;

    //@Column(name = "has_other_house")
    private Boolean hasOtherHouse;
}
