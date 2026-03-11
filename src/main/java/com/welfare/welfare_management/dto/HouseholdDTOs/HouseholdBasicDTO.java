package com.welfare.welfare_management.dto.HouseholdDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdBasicDTO {

    // ── Household core ──────────────────────────────────────────
    private Integer householdId;
    private String address;
    private String district;
    private String gnDivision;
    private Long applicationId;
    private String headNic;
}
