package com.welfare.welfare_management.dto;

import com.welfare.welfare_management.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private Long applicationId;
    private Status status;
    private LocalDate appliedDate;
    private String citizenNic;
    private String welfareType;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
