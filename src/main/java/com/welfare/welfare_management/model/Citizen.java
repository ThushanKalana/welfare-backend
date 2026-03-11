package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {
    @Id
    private String citizenNic;

    // Remove @GeneratedValue - not allowed on non-PK fields
    @Column(name = "citizen_id", unique = true, insertable = false, updatable = false)
    private Integer citizenId;          // ✅ AUTO_INCREMENT UNIQUE only

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String mobile;
    private String qrCode;
    private Double latitude;
    private Double longitude;
}
