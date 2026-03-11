package com.welfare.welfare_management.controller;

import com.welfare.welfare_management.dto.AuthDTO;
import com.welfare.welfare_management.dto.LoginDTO;
import com.welfare.welfare_management.dto.UserDTO;
import com.welfare.welfare_management.sequrity.JwtUtil;
import com.welfare.welfare_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //Get all users
//    @GetMapping("getUsers")
//    public List<UserDTO> getUsers(@RequestHeader("Authorization") String authHeader){
//        return userService.getAllUsers(authHeader);
//    }
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String authHeader){
        try {
            return ResponseEntity.ok(userService.getAllUsers(authHeader));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    //Add user
//    @PostMapping("/addUser")
//    public UserDTO createUser(@RequestHeader("Authorization") String authHeader,@RequestBody UserDTO userDTO){
//        return userService.createUser(authHeader,userDTO);
//    }
    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@RequestHeader("Authorization") String authHeader,@RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.ok(userService.createUser(authHeader,userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    //Update user
//    @PutMapping ("/updateUser")
//    public UserDTO updateUser(@RequestHeader("Authorization") String authHeader,@RequestBody UserDTO userDTO){
//        return userService.updateUser(authHeader,userDTO);
//    }
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authHeader,@RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.ok(userService.updateUser(authHeader,userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Delete user by id
    @DeleteMapping("/deleteUser/{userId}")
//    public ResponseEntity<Map<String, String>> deleteUser(@RequestHeader("Authorization") String authHeader,@PathVariable String userId) {
//        String message = userService.deleteUser(authHeader,userId);
//        return ResponseEntity.ok(Map.of("message", message));
//    }
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader,@PathVariable String userId){
        try {
            String message = userService.deleteUser(authHeader,userId);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    //Get user by id
    @GetMapping("/getUser/{userId}")
//    public UserDTO getUserById(@RequestHeader("Authorization") String authHeader,@PathVariable String userId){
//        return userService.getUsersById(authHeader,userId);
//    }
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String authHeader,@PathVariable String userId){
        try {
            return ResponseEntity.ok(userService.getUsersById(authHeader,userId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //User login and request Otp
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(userService.requestOtp(loginDTO.getNic(), loginDTO.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //GN login and request Otp
    @PostMapping("/gnLogin")
    public ResponseEntity<?> gnLogin(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(userService.gnRequestOtp(loginDTO.getNic(), loginDTO.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Verify otp
    @PostMapping("/verify-otp")
//    public AuthDTO verifyOtp(@RequestBody LoginDTO loginDTO) {
//        return userService.verifyOtp(loginDTO.getNic(), loginDTO.getOtp());
//        //return ResponseEntity.ok(auth);
//    }
    public ResponseEntity<?> verifyOtp(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(userService.verifyOtp(loginDTO.getNic(), loginDTO.getOtp()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //Logout user
    @PostMapping("/logout")
//    public ResponseEntity<Map<String, String>> logout(
//            @RequestHeader("Authorization") String authHeader,
//            @RequestBody(required = false) Map<String, String> body
//    ) {
//        String refreshToken = (body != null) ? body.get("refreshToken") : null;
//        userService.logout(authHeader, refreshToken);
//
//        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
//    }

    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) Map<String, String> body
    ) {
        try {
            String refreshToken = (body != null) ? body.get("refreshToken") : null;
            userService.logout(authHeader, refreshToken);
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    //Update user status
    @PutMapping("/admin/user-status/{userId}")
//    public ResponseEntity<Map<String, String>> changeStatus(
//            @RequestHeader("Authorization") String authHeader,
//            @PathVariable String userId,
//            //@RequestParam String role,     // <-- role passed from UI/frontend
//            //@RequestParam boolean active
//            @RequestBody Map<String, Boolean> requestBody
//    ) {
//        boolean active = requestBody.get("active");
//        String result = userService.updateAccountStatus(authHeader, userId, active);
//        return ResponseEntity.ok(Map.of("message", result));
//    }

    public ResponseEntity<?> changeStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String userId,
            @RequestBody Map<String, Boolean> requestBody
   ) {
        try {
            boolean active = requestBody.get("active");
            String result = userService.updateAccountStatus(authHeader, userId, active);
            return ResponseEntity.ok(Map.of("message", result));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

}
