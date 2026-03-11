package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.dto.HouseholdDTOs.HouseBuildingStatusDTO;
import com.welfare.welfare_management.model.HouseholdModels.HouseBuildingStatus;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseBuildingStatusRepo extends CrudRepository<HouseBuildingStatus, Integer> {

    Optional<HouseBuildingStatus> findByHouseholdId(Integer householdId);

    //Optional<HouseBuildingStatus> findByHousehold(Household household);

}
