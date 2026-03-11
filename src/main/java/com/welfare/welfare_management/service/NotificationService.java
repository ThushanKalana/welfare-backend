package com.welfare.welfare_management.service;

import com.welfare.welfare_management.dto.NotificationDTO;
import com.welfare.welfare_management.model.Notification;
import com.welfare.welfare_management.repo.CitizenRepo;
import com.welfare.welfare_management.repo.NotificationRepo;
import com.welfare.welfare_management.sequrity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;


    // Validate JWT (reuse method)
    private void validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    // Send notification
    public NotificationDTO sendNotification(String authHeader, NotificationDTO dto) {

        validateToken(authHeader);

        citizenRepo.findById(dto.getCitizenNic())
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + dto.getCitizenNic()));

        Notification notification = new Notification();
        notification.setCitizenNic(dto.getCitizenNic());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setEmail(dto.getEmail());

        emailService.sendNotificationEmail(dto.getEmail(),dto.getTitle(),dto.getMessage());

        return mapToDTO(notificationRepo.save(notification));
    }

    // Get all notifications for a citizen
    public List<NotificationDTO> getAllNotifications(String authHeader,String citizenNic) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        return notificationRepo
                .findByCitizenNicOrderByCreatedAtDesc(citizenNic)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Delete single notification
    public String deleteNotification(String authHeader,Long notificationId) {

        validateToken(authHeader);

        notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException(
                        "Notification not found with ID: " + notificationId));

        notificationRepo.deleteById(notificationId);
        return "Notification deleted successfully";
    }

    // Delete all notifications for a citizen
    public String deleteAllNotifications(String authHeader,String citizenNic) {

        validateToken(authHeader);

        citizenRepo.findById(citizenNic)
                .orElseThrow(() -> new RuntimeException(
                        "Citizen not found with NIC: " + citizenNic));

        notificationRepo.deleteByCitizenNic(citizenNic);
        return "All notifications deleted successfully";
    }

    // Helper
    private NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setCitizenNic(notification.getCitizenNic());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setEmail(notification.getEmail());
        return dto;
    }
}
