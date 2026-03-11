package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdMonthlyIncome;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdMonthlyIncomeRepo extends CrudRepository<HouseholdMonthlyIncome, Integer> {

    Optional<HouseholdMonthlyIncome> findByHouseholdId(Integer householdId);

    //Optional<HouseholdMonthlyIncome> findByHousehold(Household household);

}
