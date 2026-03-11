package com.welfare.welfare_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String token;
    private Date expiryDate;
    private boolean revoked;


    // ✅ Add this constructor for saveToken()
    public RefreshToken(String userId, String token, Date expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
        this.revoked = false; // default value
    }

}
