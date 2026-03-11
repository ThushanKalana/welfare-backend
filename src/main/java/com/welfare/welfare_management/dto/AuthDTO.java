package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class AuthDTO {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String nic;
    private String name;
    private String email;
    private Role role;
    private boolean active;
    private Long accessTokenExpiresAt;
    private Long refreshTokenExpiresAt;
}
