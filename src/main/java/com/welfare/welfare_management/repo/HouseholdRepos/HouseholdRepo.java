package com.welfare.welfare_management.repo.HouseholdRepos;

import com.welfare.welfare_management.model.HouseholdModels.Household;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HouseholdRepo extends JpaRepository<Household,String> {

    @Query(value = "SELECT * FROM household WHERE household_id=?1", nativeQuery = true)
    Optional<Household> findByHouseholdId(Integer id);

    @Query(value = "SELECT * FROM household WHERE head_nic=?1", nativeQuery = true)
    Optional<Household> findByHeadNic(String nic);

    @Modifying
    @Query("UPDATE Household h SET " +
            "h.headNic = :headNic, " +
            "h.address = :address, " +
            "h.district = :district, " +
            "h.gnDivision = :gnDivision, " +
            "h.applicationId = :applicationId," +
            "h.status = :status " +
            "WHERE h.householdId = :householdId")
    int updateHouseholdById(
            Integer householdId,
            String headNic,
            String address,
            String district,
            String gnDivision,
            Long applicationId,
            HouseholdStatus status
    );


    @Query(value = "SELECT * FROM household WHERE application_id=?1", nativeQuery = true)
    Optional<Household>findByApplicationId(Long applicationId);

//    @Query(value = "SELECT * FROM applications WHERE application_id=?1", nativeQuery = true)
//    Optional<Application> findByApplicationId(Long applicationId);

    // HouseholdRepo
    @Modifying
    @Query("DELETE FROM Household h WHERE h.headNic = :headNic")
    void deleteByHeadNic(@Param("headNic") String headNic);

}
