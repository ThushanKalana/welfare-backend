package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String password;
    private String name;
    private String nic;
    private String email;
    private String phone;
    //private String role;

    private Role role;

    private boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
