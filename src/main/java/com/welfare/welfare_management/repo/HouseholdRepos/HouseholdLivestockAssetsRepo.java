package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdLivestockAssets;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdLivestockAssetsRepo extends CrudRepository<HouseholdLivestockAssets, Integer> {

    Optional<HouseholdLivestockAssets> findByHouseholdId(Integer householdId);

    //Optional<HouseholdLivestockAssets> findByHousehold(Household household);

}
