package com.welfare.welfare_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "notification_id")
    private Long notificationId;

    //@Column(name = "citizen_nic")
    private String citizenNic;

    //@Column(name = "title")
    private String title;

    //@Column(name = "message")
    private String message;

    //@Column(name = "created_at")
    private LocalDateTime createdAt;

    private String email;

}