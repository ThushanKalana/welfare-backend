package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdBasicDTO;
import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDTO {
    private String citizenNic;
    private Integer citizenId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String mobile;
    private String qrCode;

    private ApplicationDTO application;
    private HouseholdBasicDTO household;

    private Double latitude;
    private Double longitude;

}
