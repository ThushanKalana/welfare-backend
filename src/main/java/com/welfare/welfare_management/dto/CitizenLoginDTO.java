package com.welfare.welfare_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenLoginDTO {

    private String citizenNic;
    private String otp;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresAt;
    private Long refreshTokenExpiresAt;
}
