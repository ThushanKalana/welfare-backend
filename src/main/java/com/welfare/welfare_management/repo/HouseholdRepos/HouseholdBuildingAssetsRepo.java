package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.dto.HouseholdDTOs.HouseBuildingStatusDTO;
import com.welfare.welfare_management.model.HouseholdModels.HouseBuildingStatus;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdBuildingAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdBuildingAssetsRepo extends JpaRepository<HouseholdBuildingAssets, Integer> {

    Optional<HouseholdBuildingAssets> findByHouseholdId(Integer householdId);

    //Optional<HouseholdBuildingAssets> findByHousehold(Household household);

}
