package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    private String citizenNic;
    private Double latitude;
    private Double longitude;
}
