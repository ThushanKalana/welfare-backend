package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.Application;
import com.welfare.welfare_management.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application,String> {

    @Query(value = "SELECT * FROM applications WHERE application_id=?1", nativeQuery = true)
    Optional<Application> findByApplicationId(Long applicationId);

    @Query(value = "SELECT * FROM applications WHERE status=?1", nativeQuery = true)
    List<Application> findByStatus(Status status);

    @Query(value = "SELECT * FROM applications WHERE citizen_nic=?1", nativeQuery = true)
    Optional<Application> findByCitizenNic(String citizenNic);


    @Modifying
    @Transactional
    @Query("UPDATE Application a SET " +
            "a.welfareType = COALESCE(:welfareType, a.welfareType), " +
            "a.citizenNic = COALESCE(:citizenNic, a.citizenNic), " +
            "a.status = COALESCE(:status, a.status) " +
            "WHERE a.applicationId = :applicationId")
    int updateApplicationById(
            @Param("applicationId") Long applicationId,
            @Param("welfareType") String welfareType,
            @Param("citizenNic") String citizenNic,
            @Param("status") Status status
    );

    @Modifying
    @Query("DELETE FROM Application a WHERE a.citizenNic = :citizenNic")
    void deleteByCitizenNic(@Param("citizenNic") String citizenNic);

}
