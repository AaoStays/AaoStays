package com.aao.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("AaoStays <kottalarishi4@gmail.com>"); 
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // HTML enabled

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    
    public void sendEmailVerificationLink(String toEmail, String verificationLink) {
        String html = "<p>Click below to verify your email:</p>"
                + "<a href='" + verificationLink + "'>Verify Email</a>";

        sendHtmlEmail(toEmail, "Verify Your Email - AaoStays", html);
    }

 
    public void sendLoginNotification(String toEmail, String userName) {
        String html = "<p>Hello <b>" + userName + "</b>,</p>"
                + "<p>You have successfully logged in to AaoStays.</p>"
                + "<p>If this wasn't you, please reset your password immediately.</p>";

        sendHtmlEmail(toEmail, "Login Alert - AaoStays", html);
    }

   
    public void sendBookingEmail(
            String toEmail,
            String userName,
            String bookingReference,
            String confirmationCode,
            String propertyName,
            LocalDate checkIn,
            LocalDate checkOut,
            BigDecimal totalAmount) {

        String html =
                "<p>Dear <b>" + userName + "</b>,</p>"
                + "<p>Your booking with <b>AaoStays</b> has been confirmed!</p>"
                + "<h3>Booking Details</h3>"
                + "<p>"
                + "<b>Booking Reference:</b> " + bookingReference + "<br>"
                + "<b>Confirmation Code:</b> " + confirmationCode + "<br>"
                + "<b>Property:</b> " + propertyName + "<br>"
                + "<b>Check-In:</b> " + checkIn + "<br>"
                + "<b>Check-Out:</b> " + checkOut + "<br>"
                + "<b>Total Amount:</b> ₹" + totalAmount + "<br>"
                + "</p>"
                + "<p>We look forward to hosting you soon.</p>"
                + "<p>If you didn't make this booking, please contact support immediately.</p>"
                + "<br><p>Warm regards,<br><b>AaoStays Team</b></p>";

        sendHtmlEmail(toEmail, "Booking Confirmation - " + bookingReference, html);
    }

  
    public void sendCancellationEmail(
            String toEmail,
            String userName,
            String propertyName) {

        String html =
                "<p>Hi <b>" + userName + "</b>,</p>"
                + "<p>Your booking at <b>" + propertyName + "</b> has been cancelled.</p>"
                + "<p>If you didn't perform this cancellation, contact support immediately.</p>"
                + "<br><p>Regards,<br><b>AaoStays Team</b></p>";

        sendHtmlEmail(toEmail, "Booking Cancelled - AaoStays", html);
    }
}
