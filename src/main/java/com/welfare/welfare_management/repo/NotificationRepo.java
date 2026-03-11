package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByCitizenNicOrderByCreatedAtDesc(String citizenNic);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.citizenNic = :citizenNic")
    void deleteByCitizenNic(@Param("citizenNic") String citizenNic);
}
