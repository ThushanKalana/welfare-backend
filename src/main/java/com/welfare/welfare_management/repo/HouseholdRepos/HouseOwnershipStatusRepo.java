package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.dto.HouseholdDTOs.HouseOwnershipStatusDTO;
import com.welfare.welfare_management.model.HouseholdModels.HouseOwnershipStatus;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseOwnershipStatusRepo extends CrudRepository<HouseOwnershipStatus, Integer> {

    Optional<HouseOwnershipStatus> findByHouseholdId(Integer householdId);

    //Optional<HouseOwnershipStatus> findByHousehold(Household household);

}
