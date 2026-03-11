package com.welfare.welfare_management.repo;

import com.welfare.welfare_management.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(String userId);
    void deleteByToken(String token);
}
