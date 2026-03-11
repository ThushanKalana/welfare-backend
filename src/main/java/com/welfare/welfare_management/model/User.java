package com.welfare.welfare_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private String nic;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;


}
