package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.OfficerStatus;
import com.welfare.welfare_management.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OfficerStatusRepo extends CrudRepository<OfficerStatus, Long> {

    Optional<OfficerStatus> findByApplicationId(Long applicationId);

    // Find all pending for each officer type
    List<OfficerStatus> findByGnOfficerStatus(ReviewStatus status);
    List<OfficerStatus> findBySecondOfficerStatus(ReviewStatus status);
    List<OfficerStatus> findByAdminStatus(ReviewStatus status);
}