package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "applications")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate appliedDate;

    private String citizenNic;

    private String welfareType;



}
