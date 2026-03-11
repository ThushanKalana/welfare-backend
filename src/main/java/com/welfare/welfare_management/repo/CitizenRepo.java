package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.model.Role;
import com.welfare.welfare_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CitizenRepo extends JpaRepository<Citizen,String> {

    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET " +
            "c.firstName = COALESCE(:firstName, c.firstName), " +
            "c.lastName = COALESCE(:lastName, c.lastName), " +
            "c.address = COALESCE(:address, c.address), " +
            "c.email = COALESCE(:email, c.email), " +
            "c.mobile = COALESCE(:mobile, c.mobile), " +
            "c.qrCode = COALESCE(:qrCode, c.qrCode) " +
            "WHERE c.citizenNic = :nic")

    int updateCitizenByNic(
            String nic,
            String firstName,
            String lastName,
            String address,
            String email,
            String mobile,
            String qrCode
    );

    @Query(value = "SELECT * FROM Citizen WHERE citizen_nic=?1", nativeQuery = true)
//    Citizen findByNic(String citizenNic);
    Optional<Citizen> getCitizenByNic(String citizenNic);
}
