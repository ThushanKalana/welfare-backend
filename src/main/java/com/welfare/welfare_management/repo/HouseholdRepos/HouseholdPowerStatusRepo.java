package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdPowerStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdPowerStatusRepo extends CrudRepository<HouseholdPowerStatus, Integer> {

    Optional<HouseholdPowerStatus> findByHouseholdId(Integer householdId);

    //Optional<HouseholdPowerStatus> findByHousehold(Household household);

}
