package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.dto.CitizenDTO;
import com.welfare.welfare_management.dto.CitizenLoginDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.LocationDTO;
import com.welfare.welfare_management.model.Citizen;
import com.welfare.welfare_management.model.User;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import com.welfare.welfare_management.service.CitizenLoginService;
import com.welfare.welfare_management.service.CitizenService;
import com.welfare.welfare_management.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.welfare.welfare_management.model.Role.CITIZEN;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")
public class CitizenController {

    @Autowired
    private CitizenService  citizenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CitizenRepo citizenRepo;


    @GetMapping("/getAllCitizens")
    public ResponseEntity<?> getAllCitizens(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(citizenService.getAllCitizens(authHeader));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/addCitizen")
    public ResponseEntity<?> addUCitizen( @RequestBody CitizenDTO citizenDTO){
        try {
            return ResponseEntity.ok(citizenService.createCitizen(citizenDTO));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Update citizen
//    @PutMapping ("/updateCitizen")
//    public CitizenDTO updateCitizen(@RequestHeader("Authorization") String authHeader,@RequestBody CitizenDTO citizenDTO){
//        return citizenService.updateCitizen(authHeader,citizenDTO);
//    }
//    @PutMapping ("/updateCitizen")
//    public ResponseEntity<?> updateCitizen(@RequestHeader("Authorization") String authHeader,@RequestBody CitizenDTO citizenDTO){
//        try {
//            return ResponseEntity.ok(citizenService.updateCitizen(authHeader,citizenDTO));
//        } catch (RuntimeException e) {
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(Map.of("message", e.getMessage()));
//        }
//    }

    @PutMapping("/updateCitizen")
    public ResponseEntity<?> updateCitizen(@RequestHeader("Authorization") String authHeader,@RequestBody CitizenDTO dto) {
        try {
            return ResponseEntity.ok(citizenService.updateCitizen(authHeader, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Delete citizen by nic
//    @DeleteMapping("/deleteCitizen/{citizenNic}")
//    public ResponseEntity<Map<String, String>> deleteCitizen(@RequestHeader("Authorization") String authHeader, @PathVariable String citizenNic){
//        String message = citizenService.deleteCitizen(authHeader,citizenNic);
//        return ResponseEntity.ok(Map.of("message", message));
//    }
    @DeleteMapping("/deleteCitizen/{citizenNic}")
    public ResponseEntity<?> deleteCitizen(@RequestHeader("Authorization") String authHeader, @PathVariable String citizenNic){
        try {
            String message = citizenService.deleteCitizen(authHeader,citizenNic);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Get citizen by nic
//    @GetMapping("/getCitizen/{citizenNic}")
//    public CitizenDTO getCitizenByNic(@RequestHeader("Authorization") String authHeader, @PathVariable String citizenNic){
//        return citizenService.getCitizenByNic(authHeader,citizenNic);
//    }
//    @GetMapping("/getCitizen/{citizenNic}")
//    public ResponseEntity<?> getCitizenByNic(@RequestHeader("Authorization") String authHeader, @PathVariable String citizenNic){
//        try {
//            return ResponseEntity.ok(citizenService.getCitizenByNic(authHeader,citizenNic));
//        } catch (RuntimeException e) {
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(Map.of("message", e.getMessage()));
//        }
//    }

    @GetMapping("/getCitizen/{citizenNic}")
    public ResponseEntity<?> getCitizenByNic(@RequestHeader("Authorization") String authHeader,
            @PathVariable String citizenNic) {
        try {
            return ResponseEntity.ok(citizenService.getCitizenByNic(authHeader,citizenNic));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //update citizen location
    @PutMapping("/updateLocation/{nic}")
    public ResponseEntity<?> updateLocation(@RequestHeader("Authorization") String authHeader,
            @PathVariable String nic,
            @RequestBody LocationDTO locationDTO) {
        try {
            return ResponseEntity.ok(
                    citizenService.updateLocation(authHeader, nic, locationDTO.getLatitude(), locationDTO.getLongitude()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    @Autowired
    private CitizenLoginService citizenLoginService;

    @PostMapping("/citizen/login")
    public ResponseEntity<?> login(@RequestBody CitizenLoginDTO dto) {

        try {
            return ResponseEntity.ok(citizenLoginService.requestOtp(dto.getCitizenNic()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage(), "status", false));
        }
    }

    @PostMapping("/citizen/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody CitizenLoginDTO dto) {

        try {
            return ResponseEntity.ok(citizenLoginService.verifyOtp(dto.getCitizenNic(), dto.getOtp()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage(), "status", false));
        }
    }


    //Logout citizen
    @PostMapping("/citizen/logout")

    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) Map<String, String> body
    ) {
        try {
            String refreshToken = (body != null) ? body.get("refreshToken") : null;
            citizenLoginService.logout(authHeader, refreshToken);
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Refresh token
    @PostMapping("/citizen/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (!refreshTokenService.isValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired refresh token"));
        }

        String username = jwtUtil.extractUsername(refreshToken);
//        Citizen citizen = citizenRepo.findByNic(username);

        Citizen citizen = citizenRepo.getCitizenByNic(username)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        // Revoke old token
        refreshTokenService.revokeToken(refreshToken);

        // Generate tokens using JwtUtil
        String newAccessToken = jwtUtil.generateAccessToken(citizen.getCitizenNic(), CITIZEN);
        String newRefreshToken = jwtUtil.generateRefreshToken(citizen.getCitizenNic());

        // Save new refresh token in DB
        refreshTokenService.saveToken(citizen.getCitizenNic(), newRefreshToken, 1000L * 60 * 60 * 24 * 7);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        response.put("expiresAt", System.currentTimeMillis() + 1000L * 60 * 60); // 1 hour
        return ResponseEntity.ok(response);
    }


}
