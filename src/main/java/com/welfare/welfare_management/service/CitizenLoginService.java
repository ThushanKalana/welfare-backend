package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.CitizenDTO;
import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.welfare.welfare_management.model.Role.CITIZEN;

@Service
public class CitizenLoginService {

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();

    // Step 1: Request OTP
    public Map<String, Object> requestOtp(String nic) {

        Citizen citizen = citizenRepo.getCitizenByNic(nic)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));

        otpStore.put(nic, otp);
        otpExpiry.put(nic, System.currentTimeMillis() + (5 * 60 * 1000));

        emailService.sendOtpEmail(citizen.getEmail(), otp);

        return Map.of(
                "message", "OTP sent successfully",
                "status", true
        );
    }

    // Step 2: Verify OTP
    public Map<String, Object> verifyOtp(String nic, String otp) {

        String storedOtp = otpStore.get(nic);
        Long expiry = otpExpiry.get(nic);

        if (storedOtp == null || expiry == null || expiry < System.currentTimeMillis()) {
            throw new RuntimeException("OTP expired or invalid");
        }

        if (!storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        otpStore.remove(nic);
        otpExpiry.remove(nic);

        // Fetch citizen safely
        Citizen citizen = citizenRepo.getCitizenByNic(nic)
                .orElseThrow(() ->
                        new RuntimeException("Citizen not found with NIC: " + nic)
                );


        // Generate tokens using JwtUtil
        String accessToken = jwtUtil.generateAccessToken(citizen.getCitizenNic(), CITIZEN);
        String refreshToken = jwtUtil.generateRefreshToken(citizen.getCitizenNic());

        long accessTokenExpiry = System.currentTimeMillis() + (1000 * 60 * 60);        // 1 hour
        long refreshTokenExpiry = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7); // 7 days

        // ✅ Save refresh token in DB
        refreshTokenService.saveToken(citizen.getCitizenNic(), refreshToken, refreshTokenExpiry - System.currentTimeMillis());

        System.out.println("✅ Saved refresh token for user NIC: " + citizen.getCitizenNic());

        //Citizen citizen = citizenRepo.getCitizenByNic(citizenNIC);

        CitizenDTO citizenDTO = modelMapper.map(citizen, CitizenDTO.class);

        return Map.of(
                "message", "Login successful",
                "status", true,
                "citizen",citizenDTO,
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    //Logout user
    private final Map<String, Long> tokenBlacklist = new ConcurrentHashMap<>();

    public void logout(String authHeader, String refreshToken) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");
        long expiresAt = jwtUtil.extractExpiration(token).getTime();
        tokenBlacklist.put(token, expiresAt);

        // ✅ Revoke that one refresh token only
        if (refreshToken != null && !refreshToken.isBlank()) {
            refreshTokenService.revokeToken(refreshToken);

            System.out.println("Revoked refresh token: " + refreshToken);
        }
    }
}

