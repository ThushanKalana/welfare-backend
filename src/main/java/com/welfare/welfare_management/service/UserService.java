package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.AuthDTO;
import com.welfare.welfare_management.dto.UserDTO;
import com.welfare.welfare_management.model.User;
import com.welfare.welfare_management.repo.UserRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class UserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    //Get all users
    public List<UserDTO> getAllUsers(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        //  Validate JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        String role = jwtUtil.extractRole(token);

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update users");
        }

        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    //Add user
    public UserDTO createUser(String authHeader, UserDTO userDTO) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        //  Validate JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Check duplicates
        if (userRepo.existsById(userDTO.getId())) {
            throw new RuntimeException("User already exists with ID: " + userDTO.getId());
        }

        if (userRepo.findByNic(userDTO.getNic()) != null) {
            throw new RuntimeException("NIC already registered");
        }

        if (userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepo.findByPhone(userDTO.getPhone()) != null) {
            throw new RuntimeException("Phone number already registered");
        }

        String role = jwtUtil.extractRole(token);

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update users");
        }

        User user = modelMapper.map(userDTO, User.class);
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        //user.setId(UUID.randomUUID().toString());
        userRepo.save(user);
        userDTO.setPassword(null);
        return userDTO;
    }


    //Update user
        public UserDTO updateUser(String authHeader,UserDTO userDTO) {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }

            String token = authHeader.replace("Bearer ", "");

            //  Validate JWT
            if (!jwtUtil.validateToken(token)) {
                throw new RuntimeException("Invalid or expired token");
            }

            if (userDTO.getId() == null || userDTO.getId().trim().isEmpty()) {
                throw new RuntimeException("User ID is required");
            }

//            // Check user exists
//            User updatedUser = userRepo.findById(userDTO.getId())
//                    .orElseThrow(() -> new RuntimeException(
//                            "User not found with id: " + userDTO.getId()));

            // Check duplicates (except same user)
            if (userRepo.existsByEmailAndIdNot(userDTO.getEmail(), userDTO.getId())) {
                throw new RuntimeException("Email already registered");
            }

            if (userRepo.existsByNicAndIdNot(userDTO.getNic(), userDTO.getId())) {
                throw new RuntimeException("NIC already registered");
            }

            if (userRepo.existsByPhoneAndIdNot(userDTO.getPhone(), userDTO.getId())) {
                throw new RuntimeException("Phone already registered");
            }

            String role = jwtUtil.extractRole(token);

            if (!role.equals("ADMIN")) {
                throw new RuntimeException("Only ADMIN can update users");
            }


            // Encrypt password only if not null
            String encryptedPassword = null;
            if (userDTO.getPassword() != null) {
                encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            }

            // Execute update
            int updated = userRepo.updateUserById(
                    userDTO.getId(),
                    encryptedPassword,
                    userDTO.getEmail(),
                    userDTO.getName(),
                    userDTO.getNic(),
                    userDTO.getPhone(),
                    userDTO.getRole(),
                    userDTO.getActive() // Boolean type
            );

            if (updated == 0) {
                throw new RuntimeException("User not found or nothing updated");
            }

             //Fetch the updated user
            User updatedUser = userRepo.findById(userDTO.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));

            // Map to DTO for response
            UserDTO responseDTO = modelMapper.map(updatedUser, UserDTO.class);

            // Return updated data from DTO itself (password hidden)
            responseDTO.setPassword(null);

        return responseDTO;
    }

    //Delete user by id
    public String deleteUser(String authHeader,String userId) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        //  Validate JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Validate input
        if (userId == null || userId.trim().isEmpty()) {
            throw new RuntimeException("User ID cannot be null or empty");
        }

        // Check user exists
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        String role = jwtUtil.extractRole(token);

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update users");
        }

        userRepo.deleteById(userId);

        return "User deleted successfully";
    }



    //Get user by id
    public UserDTO getUsersById(String authHeader,String userId) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        //  Validate JWT
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        if (userId == null || userId.trim().isEmpty()) {
            throw new RuntimeException("User ID cannot be null or empty");
        }

        String role = jwtUtil.extractRole(token);

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can update users");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with ID: " + userId));

        //User user = userRepo.getUserById(userId);
        return modelMapper.map(user, UserDTO.class);
    }


    //User login and request Otp
    // Temporary storage for OTPs
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();

    //  New method for login for otp
    public Map<String, Object> requestOtp(String nic, String password) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepo.findByNic(nic);
        if (user == null) {
            response.put("message", "User not found");
            response.put("status", false);
            System.out.println(response);
            return response;

            //throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("message", "Invalid credentials");
            response.put("status", false);
            System.out.println(response);
            return response;
            //throw new RuntimeException("Invalid credentials");
        }

        if(user.isActive()) {
            // Generate 6-digit OTP
            String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
            otpStore.put(nic, otp);
            otpExpiry.put(nic, System.currentTimeMillis() + (5 * 60 * 1000)); // 5 min expiry

            // Send OTP via email
            emailService.sendOtpEmail(user.getEmail(), otp);

            System.out.println("user status activated " +user.isActive());
            response.put("message", "OTP sent to your email");
            response.put("status", user.isActive());
            return response;
        }else{
        //return "OTP sent to your email";
        System.out.println("user status " +user.isActive());
        response.put("message", "You are not activated!");
        response.put("status ", user.isActive());
        return response;
        }
    }



    public Map<String, Object> gnRequestOtp(String nic, String password) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepo.findByNic(nic);
        if (user == null) {
            response.put("message", "User not found");
            response.put("status", false);
            return response;
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("message", "Invalid credentials");
            response.put("status", false);
            return response;
        }

        // Fix #1: use .equals() for String comparison, not ==
        if (!"GN_OFFICER".equals(user.getRole().toString())) {
            response.put("message", "You are not authorized to login.");
            response.put("status", false);
            return response;
        }

        if (!user.isActive()) {
            response.put("message", "Your account is not activated.");
            response.put("status", false);
            return response;
        }

        // All checks passed — generate and send OTP
        // Generate 6-digit OTP
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        otpStore.put(nic, otp);
        otpExpiry.put(nic, System.currentTimeMillis() + (5 * 60 * 1000)); // 5 min expiry
        // Send OTP via email
        emailService.sendOtpEmail(user.getEmail(), otp);
        // Fix #2: no trailing spaces in keys
        response.put("message", "OTP sent to your email");
        response.put("status", user.isActive());
        return response;
    }


    //Verify otp
    public AuthDTO verifyOtp(String nic, String otp) {
        String storedOtp = otpStore.get(nic);
        Long expiry = otpExpiry.get(nic);

        if (storedOtp == null || expiry == null || expiry < System.currentTimeMillis()) {
            throw new RuntimeException("OTP expired or invalid");
        }

        if (!storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // Clear OTP after successful verification
        otpStore.remove(nic);
        otpExpiry.remove(nic);

        // Fetch user info
        User user = userRepo.findByNic(nic);
        if (user == null) {
            throw new RuntimeException("User not found for NIC: " + nic);
        }

        // Generate tokens using JwtUtil
        String accessToken = jwtUtil.generateAccessToken(user.getNic(),user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getNic());

        long accessTokenExpiry = System.currentTimeMillis() + (1000 * 60 * 60);        // 1 hour
        long refreshTokenExpiry = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7); // 7 days

        //  Save refresh token in DB
        refreshTokenService.saveToken(user.getNic(), refreshToken, refreshTokenExpiry - System.currentTimeMillis());

        System.out.println(" Saved refresh token for user NIC: " + user.getNic());


        // Return both tokens in response
        return new AuthDTO(
                accessToken,
                refreshToken,
                user.getId(),
                user.getNic(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                accessTokenExpiry,
                refreshTokenExpiry
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

            //  Revoke that one refresh token only
            if (refreshToken != null && !refreshToken.isBlank()) {
                refreshTokenService.revokeToken(refreshToken);

                System.out.println("Revoked refresh token: " + refreshToken);
            }
        }


    //  Check if token is blacklisted
        public boolean isTokenBlacklisted (String token){
            Long expiresAt = tokenBlacklist.get(token);
            if (expiresAt == null) {
                return false;
            }
            // auto-clean expired tokens
            if (expiresAt < System.currentTimeMillis()) {
                tokenBlacklist.remove(token);
                return false;
            }
            return true;
        }



    //Update user status
    public String updateAccountStatus(String authHeader, String userId, boolean status) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        // Validate token
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Extract role from token
        String tokenRole = jwtUtil.extractRole(token);
        System.out.println("tokenRole "+ tokenRole);

        // Only ADMIN can change account status
        if (!"ADMIN".equalsIgnoreCase(tokenRole)) {
            throw new RuntimeException("Only ADMIN users can change account status");
        }

        // Update in database
        int updated = userRepo.updateUserStatus(userId, status);
        if (updated == 0) {
            throw new RuntimeException("User not found");
        }

        return status ? "User activated" : "User deactivated";
    }




}


