package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdVehicleAssets;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdVehicleAssetsRepo extends CrudRepository<HouseholdVehicleAssets, Integer> {

    Optional<HouseholdVehicleAssets> findByHouseholdId(Integer householdId);

    //Optional<HouseholdVehicleAssets> findByHousehold(Household household);

}
