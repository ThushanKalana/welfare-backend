package com.welfare.welfare_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long notificationId;
    private String citizenNic;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String email;
}
