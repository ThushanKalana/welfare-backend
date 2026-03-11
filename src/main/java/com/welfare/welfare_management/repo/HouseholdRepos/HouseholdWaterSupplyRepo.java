package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.HouseholdWaterSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdWaterSupplyRepo extends CrudRepository<HouseholdWaterSupply, Integer> {

    Optional<HouseholdWaterSupply> findByHouseholdId(Integer integer);
}
