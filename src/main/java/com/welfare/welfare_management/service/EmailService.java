package com.welfare.welfare_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Login OTP");
        message.setText("Dear User,\n\nYour OTP is: " + otp +
                "\nIt will expire in 5 minutes.\n\nRegards,\nWelfare Management System");

        mailSender.send(message);
    }


    @Async
    public void sendNotificationEmail(String to, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messageBody);
        mailSender.send(message);
    }
}
