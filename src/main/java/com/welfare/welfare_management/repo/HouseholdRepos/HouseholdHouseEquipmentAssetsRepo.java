package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdHouseEquipmentAssets;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdHouseEquipmentAssetsRepo extends CrudRepository<HouseholdHouseEquipmentAssets, Integer> {

    Optional<HouseholdHouseEquipmentAssets> findByHouseholdId(Integer householdId);

    //Optional<HouseholdHouseEquipmentAssets> findByHousehold(Household household);

}
