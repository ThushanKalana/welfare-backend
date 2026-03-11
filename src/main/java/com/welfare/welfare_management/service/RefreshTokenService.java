package com.welfare.welfare_management.service;
import com.welfare.welfare_management.model.RefreshToken;
import com.welfare.welfare_management.repo.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Save new refresh token
    public RefreshToken saveToken(String userId, String token, long expiryMillis) {
        RefreshToken refreshToken = new RefreshToken(
                userId,
                token,
                new Date(System.currentTimeMillis() + expiryMillis)
        );
        return refreshTokenRepository.save(refreshToken);
    }

    // Validate refresh token
    public boolean isValid(String token) {
        Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(token);
        return storedToken.isPresent()
                && !storedToken.get().isRevoked()
                && storedToken.get().getExpiryDate().after(new Date());
    }

    // Revoke (logout)
//    public void revokeToken(String token) {
//        refreshTokenRepository.findByToken(token).ifPresent(rt -> {
//            rt.setRevoked(true);
//            refreshTokenRepository.save(rt);
//        });
//    }

    // Revoke all tokens for a user (logout-all)
    public void revokeAllForUser(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public void revokeToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}

