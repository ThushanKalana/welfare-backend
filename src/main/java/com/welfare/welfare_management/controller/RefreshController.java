package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.model.RefreshToken;
import com.welfare.welfare_management.model.User;
import com.welfare.welfare_management.repo.UserRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import com.welfare.welfare_management.service.RefreshTokenService;
import com.welfare.welfare_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")
public class RefreshController {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo  userRepo;

    // Refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (!refreshTokenService.isValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired refresh token"));
        }

        String username = jwtUtil.extractUsername(refreshToken);
        User user = userRepo.findByNic(username);

        // Revoke old token
        refreshTokenService.revokeToken(refreshToken);

        // Generate new tokens
        String newAccessToken = jwtUtil.generateAccessToken(username, user.getRole());
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        // Save new refresh token in DB
        refreshTokenService.saveToken(user.getNic(), newRefreshToken, 1000L * 60 * 60 * 24 * 7);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        response.put("expiresAt", System.currentTimeMillis() + 1000L * 60 * 60); // 1 hour
        return ResponseEntity.ok(response);
    }

}
