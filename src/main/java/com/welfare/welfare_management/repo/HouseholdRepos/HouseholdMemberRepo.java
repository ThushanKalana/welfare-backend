package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HouseholdMemberRepo extends JpaRepository<HouseholdMember, Integer> {

    List<HouseholdMember> findByHouseholdId(Integer householdId);

    //List<HouseholdMember> findByHousehold(Household household);


    @Modifying
    @Query("DELETE FROM HouseholdMember m WHERE m.householdId = :householdId")
    void deleteByHouseholdId(Integer householdId);

    boolean existsByNationalId(String nationalId);


    @Query(value = "SELECT * FROM household_members WHERE national_id=?1", nativeQuery = true)
    Optional<HouseholdMember> findByNationalId(String nationalId);

    //to allow same NIC only for updates on same member
    boolean existsByNationalIdAndMemberIdNot(String nationalId, Integer memberId);

    //@Query(value = "SELECT * FROM household_members WHERE national_id=?1", nativeQuery = true)
    //Optional<HouseholdMember> existsByNationalId(String nationalId);



}
