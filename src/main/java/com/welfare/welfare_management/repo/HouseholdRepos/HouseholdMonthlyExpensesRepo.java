package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdMonthlyExpenses;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseholdMonthlyExpensesRepo extends CrudRepository<HouseholdMonthlyExpenses, Integer> {

    Optional<HouseholdMonthlyExpenses> findByHouseholdId(Integer householdId);

    //Optional<HouseholdMonthlyExpenses> findByHousehold(Household household);

}
