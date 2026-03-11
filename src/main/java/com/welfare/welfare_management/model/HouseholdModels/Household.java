package com.welfare.welfare_management.model.HouseholdModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "household")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer householdId;

    private String address;
    private String district;
    private String gnDivision;
    private Long applicationId;
    private String headNic;
    @Enumerated(EnumType.STRING)
    private HouseholdStatus status;

}
