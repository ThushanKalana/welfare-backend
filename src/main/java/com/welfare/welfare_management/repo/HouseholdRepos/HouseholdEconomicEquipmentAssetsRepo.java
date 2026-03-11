package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdBuildingAssets;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdEconomicEquipmentAssets;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdEconomicEquipmentAssetsRepo extends CrudRepository<HouseholdEconomicEquipmentAssets, Integer> {

    Optional<HouseholdEconomicEquipmentAssets> findByHouseholdId(Integer householdId);

    //Optional<HouseholdEconomicEquipmentAssets> findByHousehold(Household household);
}
